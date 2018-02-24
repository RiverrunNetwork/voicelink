
# 语音ASR

目前支持的功能如下 具体看TellA Demo
```java
//接管语音的ASR
        btn_asr.setOnClickListener(View.OnClickListener {
            val tell = Tell()
            tell.pck = packageName
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_ASR
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //接管语音的ASR,并且不显示语音动画
        btn_asr_hide_animation.setOnClickListener(View.OnClickListener {
            val tell = Tell()
            tell.pck = packageName
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_ASR
            tell.isHideAnimation = true
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //接管语音的ASR,并且不显示语音动画,并且使用游戏引擎
        btn_asr_hide_animation_ddz_game.setOnClickListener(View.OnClickListener {
            val tell = Tell()
            tell.pck = packageName
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_ASR
            tell.isHideAnimation = true
            tell.isNeedDDZGameConversion = true
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //接管语音的ASR,并且不显示语音动画,并且使用游戏引擎,并且开启连续识别
        btn_asr_hide_animation_ddz_game_continuous_recognition.setOnClickListener(View.OnClickListener {
            val tell = Tell()
            tell.pck = packageName
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_ASR
            tell.isHideAnimation = true
            tell.isNeedDDZGameConversion = true
            tell.isEnableContinuousRecognition = true
            TellManager.getInstance().tell(App.sApp, tell)
        })
```
