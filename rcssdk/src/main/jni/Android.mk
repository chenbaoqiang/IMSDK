# Copyright (C) 2009 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE    := rcssdk
LOCAL_SRC_FILES := ../../../../../rcssdk/debug/android/librcssdk.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := crypto
LOCAL_SRC_FILES := ../../../../../rcssdk/lualibs/prebuilt/android/libmycrypto_static.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := ssl
LOCAL_SRC_FILES := ../../../../../rcssdk/lualibs/prebuilt/android/libmyssl_static.a
include $(PREBUILT_STATIC_LIBRARY)


include $(CLEAR_VARS)

LOCAL_MODULE    := RcsSdk
LOCAL_SRC_FILES := com_interrcs_sdk_RcsApi.c

LOCAL_C_INCLUDES := ../../../../../rcssdk/rcssdk/ ../../../../../rcssdk/lualibs/lua

LOCAL_STATIC_LIBRARIES := rcssdk ssl crypto
LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)
