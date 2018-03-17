MAC_DEBUG=./debug/mac
MAIN_SRC=./rcssdk/src/main
RCS_ANDROID=1
export RCS_ANDROID
.PHONY: uwsdemo usdk sdk update-gen

sdk: update-gen
	cd ../ && make bin2c 
	cd ../rcssdk && make clean -f makefile.android
	cd ../rcssdk && make -f makefile.android -j4
	cd ${MAIN_SRC}/jni && ndk-build -j4
	@echo 'build ok: ${MAIN_SRC}/libs/armeabi/libRcsSdk.so'

update-gen:
	cd ../ && make update-gen

clean:
	cd ../rcssdk && make -f makefile.android clean
	rm -f ${MAIN_SRC}/libs/armeabi/libRcsSdk.so

usdk:
	make usdk -f makefile_usdk

uwsdemo:
	buck install -r uwsdemo:app
