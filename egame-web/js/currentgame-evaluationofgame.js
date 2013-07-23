var currentGame = currentGame || {};
(function(currentGame,window,$){

    var USERID = "";
    var gameId = "";
    var gameName = "";
    var userName = "";
    //获取地址中的中文参数
    function GetQueryName(name) {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var str = decodeURI(window.location.search.substr(1));
        var r = str.match(reg);
        if (r!=null) return unescape(r[2]); return null;
    }
    var cuev = window.cuev = currentGame.evaluationofgame = {
        init:function(){
            var self = this;
            pageLoadFinish();
            USERID = GetQueryName("USERID");
            gameId = GetQueryName("gameId");
            gameName = decodeURI(GetQueryName("gameName"));
            userName = decodeURI(GetQueryName("userName"));
            self.backUrl = String.format(currentGameIndex_html,USERID,gameId);
            $("#btnCancel").attr("href",self.backUrl);
            self.getDataAndShow();
        },
        setStarLevel:function(level){
            var levelWidthMap = {"1":40,"2":90,"3":140,"4":190,"5":240};
            $("#starLevel").css("width",levelWidthMap[level]).attr("level",level);
        },
        starInit:function(){
            var self = this;
            var jstar = $("#star");
            var jstarlevel = $("#starLevel");
            var l = jstar.offset().left, w = 0, unit = 48;
            var curLevel = 1;
            function touchMove(e){
                e.preventDefault();
                w = e.changedTouches[0].clientX - l;
                if(w%unit != 0){
                    curLevel = Math.floor(w/unit) + 1;
                }
                if(curLevel>5){curLevel = 5;}
                if(curLevel<1){curLevel = 1;}
                self.setStarLevel(curLevel);
            }
            function touchEnd(e){
                e.preventDefault();
                $(document)[0].removeEventListener("touchmove",touchMove,false);
            }
            //touch事件注册
            jstar[0].addEventListener("touchstart",function(e){
                    e.preventDefault();
                    $(document)[0].addEventListener("touchmove",touchMove,false);
                    $(document)[0].addEventListener("touchend",touchEnd,false);
            },false);
            jstar[0].addEventListener("touchmove",touchMove,false);
            jstar[0].addEventListener("touchend",touchMove,false);
        },
        setDefMsg:function(){
            var self = this;
            //默认4星
            self.setStarLevel(4);
            self.starInit();
            $("#reviewCon").click(function(){
                var defaul = $(this).find(".mainMsg").html();
                var hint = "请输入您的评论内容";
                var errMsg = "请输入120个以内字符。";
                showTextDialog("",defaul,"请输入评论内容",".{1,120}",errMsg,'cuev.textCallBack("%1$s","#reviewCon")');
            }).find(".defaultMsg").removeClass("hide");
            $("#btnSubmit").click(function(){
                self.submit();
            });
        },
        isSubmit:false,
        backUrl:"",
        commMsg:{
            sendErr:"亲，提交失败,请检查输入内容哦",
            netErr:"网络异常，请检查网络"
        },
        textCallBack:function(msg,targetSelecter){
            var jtarget = $(targetSelecter);
            msg = msg.replace(/[%<>$]/g,'*');
            if (msg.length>120) {
                showToast("请输入120个以内字符。");
                msg = msg.substr(0,120);
            }
            jtarget.find(".defaultMsg").hide();
            jtarget.find(".mainMsg").html(msg);
        },
        submit:function(){
            var self = this;
            if(self.isSubmit){
                return false;
            }
            var starLevel = $("#starLevel").attr("level");
            var reviewCon = $("#reviewCon").find(".mainMsg").html();
            if(!/\S+/.test(reviewCon)){
                showToast("评论内容不能为空。");
                return false;
            }
            self.isSubmit = true;
            var url = String.format(sendEvaluationLevel_url,gameId,starLevel,USERID,reviewCon,gameName,userName);
            url = encodeURI(url);
            url = encodeURI(url);
            pageLoadStart();
            var request = $.ajax({
                url : url,
                type : "GET",
                async:true,
                success : function(data) {
                    pageLoadFinish();
                    if(data.result.resultcode != 0){
                        self.isSubmit = false;
                        showToast(self.commMsg.sendErr);
                        return false;
                    }
                    showToast("亲，提交成功");
                    window.location = self.backUrl;
                    self.isSubmit = false;
                },
                dataType : "json"
            });

            request.fail(function(textStatus)
            {
                pageLoadFinish();
                self.isSubmit = false;
                // 把LOADING 换成加载出错提示语
                showToast(self.commMsg.sendErr);
            });
        },
        fillData:function(data){
            this.setStarLevel(data.score);
            $("#star").click(function(){
                showToast("您已评价过，不需要再评价。");
            });
            $("#reviewCon").click(function(){
                showToast("您已评价过，不需要再评价。");
            }).find(".defaultMsg").html("您已评价过，不需要再评价。").removeClass("hide");
            //$("#reviewCon").find(".defaultMsg").hide();
            //$("#reviewCon").find(".mainMsg").html(data.comment);
        },
        getDataAndShow:function(){
            var self = this;
            var url = String.format(getEvaluationLevel_url,gameId,USERID);
            var request = $.ajax({
                url : url,
                type : "GET",
                success : function(data) {
                    pageLoadFinish();
                    if(data.score == 0){
                        //没有评价过，调用self.setDefMsg()设置页面初始状态
                        self.setDefMsg();
                    }else{
                        //以评价过，填充评价信息
                        self.fillData(data);
                    }
                },
                dataType : "json"
            });

            request.fail(function(){
                pageLoadFinish();
                showToast(self.commMsg.netErr);
            });
        }
    };


    $(function(){
        cuev.init();
    });

})(currentGame,window,jQuery);
