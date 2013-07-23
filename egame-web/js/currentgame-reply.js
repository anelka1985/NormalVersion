var currentGame = currentGame || {};

(function(currentGame,window,$){

    var USERID = "";
    var subjectId = null;
    var cure = window.cure = currentGame.reply= {
        isSubmit:false,
        init:function(){
            var self = this;
            pageLoadFinish();
            USERID = GetQueryString("USERID");
            subjectId = GetQueryString("subjectId");
            self.backUrl = String.format(currentgameCircleDetail_html,subjectId,USERID);
            var url = String.format(currentGameCircleDetail_url,subjectId);
            var request = $.ajax({
                url : url,
                type : "GET",
                success : function(data) {
                    pageLoadFinish();
                    $("#subjectTitle").html(data.subjectDetail.subjectTittle);
                },
                dataType : "json"
            });
            $("#btnCancel").attr("href",self.backUrl);
            $("#replyCon").click(function(){
                var defaul = $(this).find(".mainMsg").text();
                var hint = "请输入您的回复内容";
                var errMsg = "请输入120个以内字符。";
                showTextDialog("",defaul,"请输入回复内容",".{1,600}",errMsg,'cure.textCallBack("%1$s","#replyCon")');
                //self.writeMsg(this);
            });
            $("#btnSubmit").click(function(){
                self.submit();
            });
        },
        backUrl:"",
        commMsg:{
            sendErr:"亲，请检查您的输入内容，再来回复哦！"
        },
        textCallBack:function(msg,targetSelecter){
            var jtarget = $(targetSelecter);
            msg = msg.replace(/[%<>$\s]/g,'*');
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
            var replyCon = $("#replyCon").find(".mainMsg").html();
            if(!/\S+/.test(replyCon)){
                showToast("回复内容不能为空");
                return false;
            }
            self.isSubmit = true;
            var url = String.format(sendReply_url,subjectId,USERID,replyCon);
            url = encodeURI(url);
            url = encodeURI(url);
            pageLoadStart();
            var request = $.ajax({
                url : url,
                type : "GET",
                success : function(data) {
                    pageLoadFinish();
                    if(data.result.resultcode != 0){
                        self.isSubmit = false;
                        showToast(self.commMsg.sendErr);
                        return false;
                    }
                    showToast("提交成功。");
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
        }
    };

    $(function(){
        cure.init();
    });

})(currentGame,window,jQuery);
