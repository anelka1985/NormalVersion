var currentGame = currentGame || {};
(function(currentGame,window,$){
    var USERID = "";
    var gameId = "";

    var cuac = window.cuac = currentGame.achievement = {
        init:function(){
            USERID = GetQueryString("USERID");
            gameId = GetQueryString("gameId");
            if(!USERID){
                showToast("USERID is null");
            }
            this.getDataAndShow();
        },
        pageSize:15,
        totalRecord:0,
        pageNum:1,
        totalPage:1,
        isDid:false,
        commMsg:{
            netErr:'网络异常，请检查网络',
            noList:'还没有相应成就。'
        },
        showCommMsg:function(msg){
            var str = '<div class="text_pk">'+msg+'</div>';
            $("body").append(str);
        },
        bindPageBtn:function(){
            var self = this;
            var jpageHome = $("#pageHome"), jpageUp = $("#pageUp"), jpageDown = $("#pageDown"), jpageEnd = $("#pageEnd");
            jpageHome.click(function(){
                self.pageJump("home");
            });
            jpageUp.click(function(){
                self.pageJump("up");
            });
            jpageDown.click(function(){
                self.pageJump("down");
            });
            jpageEnd.click(function(){
                self.pageJump("end");
            });
        },
        pageJump:function(type){
            var self = this;
            switch(type) {
                case 'home':
                    if(self.pageNum == 1){return false}
                    self.pageNum = 1;
                    self.getDataAndShow();
                    break;
                case 'up':
                    if(self.pageNum == 1){return false}
                    self.pageNum -= 1;
                    self.getDataAndShow();
                    break;
                case 'down':
                    if(self.pageNum == self.totalPage){return false}
                    self.pageNum += 1;
                    self.getDataAndShow();
                    break;
                case 'end':
                    if(self.pageNum == self.totalPage){return false}
                    self.pageNum = self.totalPage;
                    self.getDataAndShow();
                    break;
                default:
                    self.pageNum = 0;
                    self.getDataAndShow();
            }
        },
        fillData:function(data){
            var self = this;
            //getTotalPage返回总页码
            function getTotalPage(pageNum,pageSize,totalItem){
                var pageNum = pageNum,
                    pageSize = pageSize,
                    totalItem = totalItem;
                var totalPage = Math.ceil(totalItem/pageSize);
                if(totalPage === 0){
                    totalPage = 1;
                }
                return totalPage;
            }

            if(data.result.resultcode == "0"){
                if(!self.isDid){
                    self.totalRecord = data.totalRecord;
                    self.totalPage = getTotalPage(self.pageNum,data.pageSize,self.totalRecord);
                    self.bindPageBtn();
                    self.isDid = true;
                }

                if(self.totalRecord == 0){
                    //如果数据总条数为0，显示没有数据提示并返回不再往下执行
                    self.showCommMsg(self.commMsg.noList);
                    return false;
                }
                //主题数量
                $("#numMsg").removeClass("hide");
                $("#reachedNum").html(self.totalRecord);
                //setPagesState设置分页按钮显示状态
                setPagesState(self.pageNum,self.totalPage);

                var str = "";
                for(var i in data.achieveList){
                    var item = data.achieveList[i];
                    var achievementFriendUrl = String.format(currentGameAchievementReached_html,USERID,1,item.achieveId,item.gameId);
                    str += '<div class="line_1"></div>';
                    str += '<div class="list pos"><a href="'+achievementFriendUrl+'" class="coll_bg P_tb_10 blohi">';
                    str += '<div class="collLeft pos"><span class="head_mask_76c"></span><img src="'+item.picPath+'" alt=""/></div>';
                    str += '<div class="listRight">';
                    str += '<P class="font_20 m_tb_2">'+item.achieveName;
                    if (item.myStatus == 1) {
                        str += '<span class="float_r font_16 color_2 m_r_30">'+item.myReachTime+'&nbsp;达成</span>';
                    }
                    str += '</p>';
                    str += '<P class="font_18 color_4 m_tb_2 p_r_30">'+item.achieveRemark+'</P>';
                    str += '</div>';
                    str += '<div class="narrow_r"></div>';
                    str += '</a></div>';
                }
                $("#achievementReachedCon").html(str);
            }else {
                self.showCommMsg(data.result.resultmsg);
            }
        },
        getDataAndShow:function(){
            var self = this, startIndex = (self.pageNum-1)*self.pageSize;
            var url = String.format(currentGameAchievement_url,startIndex,self.pageSize,gameId,USERID);
            var request = $.ajax({
                url : url,
                success : function(data) {
                    pageLoadFinish();
                    self.fillData(data);
                },
                dataType : "json"
            });

            request.fail(function(){
                pageLoadFinish();
                success="1";
                showToast(self.commMsg.netErr);
            });
        }
    };

    // 页面读取完毕的时候，初始化数据
    $(document).ready(function(){
        cuac.init();
    });

})(currentGame,window,jQuery);
