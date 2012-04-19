LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_PACKAGE_NAME := BMEClient
LOCAL_SRC_FILES := $(call all-java-files-under,src)

#LOCAL_CERTIFICATE := superuser

include $(BUILD_PACKAGE)

##

