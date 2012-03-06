/*
** Copyright 2012, Alexey Roslyakov <alexey.roslyakov@newsycat.com>
**
** Licensed under the Apache License, Version 2.0 (the "License");
** you may not use this file except in compliance with the License.
** You may obtain a copy of the License at
**
**     http://www.apache.org/licenses/LICENSE-2.0
**
** Unless required by applicable law or agreed to in writing, software
** distributed under the License is distributed on an "AS IS" BASIS,
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
** See the License for the specific language governing permissions and
** limitations under the License.
*/

#include <unistd.h>
#include <errno.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>

typedef unsigned int uint32;
typedef unsigned short uint16;
typedef unsigned char uint8;

struct bme_reply {
  uint32     unknown1;
  uint32     unknown2;
  uint32     charger_type; // 0 - none, 2 - usbmax500, 3 - usbwall
  uint32     unknown4;
  uint32     unknown5;
  uint32     charging_time;
  uint32     unknown7;
  uint32     unknown8;
  uint32     battery_max_level; // - cur level?
  uint32     battery_cur_level; // /
  uint32     unknown11;
  uint32     unknown12;
  uint32     battery_max_capacity;
  uint32     battery_cur_capacity;
  uint32     battery_max_voltage;
  uint32     battery_cur_voltage;
  uint32     battery_pct_level;
  uint32     battery_temperature;
  int        battery_current;
  uint32     unknown20;
  uint32     battery_last_full_capacity;

  uint8      reserved[128];

};

static void dumpBuffer(const void *buf, size_t bufSize)
{
  int cnt = 0; char *p = (char*)buf;
  while (bufSize--)
    fprintf(stderr, "%02X%s", *p++, (++cnt & 15 ? " " : "\n") );
  fprintf(stderr, "\n");
}

const char* bmeClientGetData(const char *bmeSocketPath, struct bme_reply *bmeReply)
{
  int s = socket(AF_LOCAL, SOCK_STREAM, 0);
  if (s <= 0)
    return "socket";

  struct sockaddr_un name;
  name.sun_family = AF_LOCAL;
  strcpy(name.sun_path, bmeSocketPath);
  if ( connect(s, (struct sockaddr *)&name, sizeof(name)) != 0)
    return "connect";

  char foo[256];
  size_t rd;

  // TODO: clean up this crap
  write(s, "SYNC\10\0\0\0BMentity", 16);
  rd = read(s, foo, sizeof(foo));
  fprintf(stderr, "rd=%zu\n", rd);
  
  write(s, "SYNC\4\0\0\0\3\200\0\0\n", 12);
  rd = read(s, foo, 20);
  fprintf(stderr, "rd=%zu\n", rd);
  rd = read(s, bmeReply, sizeof(*bmeReply));
  fprintf(stderr, "rd=%zu\n", rd);

  // FIXME: sometimes we get extra data in replies
  if (rd > 128)
    memmove( bmeReply, ((char*) bmeReply) + rd - 128, 128);
  dumpBuffer(bmeReply, rd);

  return 0;
}

int print_usage(const char *prog)
{
  fprintf(stderr, "Usage: %s <bme_socket_path>\n", prog);
  fprintf(stderr, "sizeof=%zu\n", sizeof (struct bme_reply));

  return -1;
}

int main(int argc, const char **argv)
{
  if (argc != 2)
    return print_usage(argv[0]);

  struct bme_reply bmeReply;
  memset(&bmeReply, 0, sizeof bmeReply);

  const char *failedOn = bmeClientGetData(argv[1], &bmeReply);
  if (failedOn) {
    fprintf(stderr, "bme_client_get_data failed on %s: %s\n", failedOn, strerror(errno));
    return -errno;
  }

  fprintf(stderr, "BME stat\n");
  fprintf(stderr, "Unknown 1,2,4,5: %u %u %u %u\n", bmeReply.unknown1, bmeReply.unknown2, bmeReply.unknown4, bmeReply.unknown5);
  fprintf(stderr, "Charger type:\t\t%u\n", bmeReply.charger_type);
  fprintf(stderr, "Charging time:\t\t%u\n", bmeReply.charging_time);
  fprintf(stderr, "Battery temperature:\t%.2f\n", bmeReply.battery_temperature - 273.15f);
  fprintf(stderr, "Battery max. Level:\t%u\n", bmeReply.battery_max_level);
  fprintf(stderr, "Battery cur. Level:\t%u\n", bmeReply.battery_cur_level);
  fprintf(stderr, "Battery pct. Level:\t%u\n", bmeReply.battery_pct_level);
  fprintf(stderr, "Battery Max. Capacity:\t%u\n", bmeReply.battery_max_capacity);
  fprintf(stderr, "Battery Cur. Capacity:\t%u\n", bmeReply.battery_cur_capacity);
  fprintf(stderr, "Battery Last Full Cap:\t%u\n", bmeReply.battery_last_full_capacity);
  fprintf(stderr, "Battery Max. Voltage:\t%u\n", bmeReply.battery_max_voltage);
  fprintf(stderr, "Battery Cur. Voltage:\t%u\n", bmeReply.battery_cur_voltage);
  fprintf(stderr, "Battery Current:\t%d\n", bmeReply.battery_current);
  fprintf(stderr, "Unknown 7,8:\t%u %u\n", bmeReply.unknown7, bmeReply.unknown7);
  fprintf(stderr, "Unknown 11,12:\t%u %u\n", bmeReply.unknown11, bmeReply.unknown12);
  fprintf(stderr, "Unknown20 (timestamp?):\t%u\n", bmeReply.unknown20);
  fprintf(stderr, "\n");

  return 0;
}
