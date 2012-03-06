LOCAL_PATH:= $(call my-dir)

#
# bmecli
#
include $(CLEAR_VARS)
LOCAL_SRC_FILES:= bmecli.c
LOCAL_MODULE:= bmecli
LOCAL_STATIC_LIBRARIES:= libc libcutils
LOCAL_MODULE_TAGS:= optional
LOCAL_FORCE_STATIC_EXECUTABLE := true
include $(BUILD_EXECUTABLE)
