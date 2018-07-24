
# VoiceTips使用方法

基本逻辑流程：
tipView.renderTips() 通知大耳朵需要显示的信息 -> 大耳朵通过IUserStatusNotice的tips方法回调给加工过的数据 -> 更新数据 tipView.updateTips()


需要引入的类库：
authentication-171210.jar
constantplugin-180718.jar
lib.sharedres-release.aar
lib.voicetip-180724.aar
thirdtell-180715.jar
voicethirdparty-180715.jar
