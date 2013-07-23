var currentGame = currentGame || {};
(function(currentGame,window,$){

    var USERID = "";
    var cuke = window.cuke = currentGame.keyNote = {
        isSubmit:false,
        init:function(){
            var self = this;
            pageLoadFinish();
            USERID = GetQueryString("USERID");
            self.backUrl = String.format(currentGameCircleIndex_html,USERID);
            $("#subjectTitle").click(function(){
                var defaul = $(this).find(".mainMsg").html();
                var hint = "请输入您的主题";
                var errMsg = "请输入30个以内字符。";
                showTextDialog("",defaul,"请输入主题标题",".{1,30}",errMsg,'cuke.textCallBack("%1$s","#subjectTitle")');
                //cuke.writeMsg(this);
            });
            $("#subjectCon").click(function(){
                var defaul = $(this).find(".mainMsg").html();
                var hint = "请输入您的评论内容";
                var errMsg = "请输入120个以内字符。";
                showTextDialog("",defaul,"请输入评论内容",".{1,120}",errMsg,'cuke.textCallBack("%1$s","#subjectCon")');
                //cuke.writeMsg(this);
            });
            $("#btnCancel").attr("href",self.backUrl);
            $("#btnSubmit").click(function(){
                self.submit();
            });
        },
        backUrl:"",
        textCallBack:function(msg,targetSelecter){
            var jtarget = $(targetSelecter);
            jtarget.find(".defaultMsg").hide();
            jtarget.find(".mainMsg").html(msg);
        },
        commMsg:{
            sendErr:"亲，提交失败,请检查输入内容哦"
        },
        submit:function(){
            var self = this;
            if(self.isSubmit){
                return false;
            }
            var subjectTitle = $("#subjectTitle").find(".mainMsg").html();
            var subjectCon = $("#subjectCon").find(".mainMsg").html();
            var isToCustomerService = !!$("#toCustomerService").attr("checked");
            if(!/\S+/.test(subjectTitle)){
                showToast("主题不能为空");
                return false;
            }
            if(!/\S+/.test(subjectCon)){
                showToast("主题内容不能为空");
                return false;
            }
            self.isSubmit = true;
            var url = String.format(sendSubject_url,subjectTitle,subjectCon,USERID);
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
                    showToast("亲，提交成功。");
                    window.location = self.backUrl;
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
        cuke.init();
    });

})(currentGame,window,jQuery);
