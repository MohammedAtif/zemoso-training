app.controller("HttpGetController", function ($scope, $http) {

    $scope.sendData = function () {
        // use $.param jQuery function to serialize data from JSON
        if(!($scope.title==undefined && $scope.content==undefined)) {

            var data = {
                user: $scope.user,
                title: $scope.title,
                content: $scope.content,
                reminder: $scope.reminderTime
            };
            $http.post('addPost', data)
                .success(function (data) {
                    //$log.debug(data);
                    $scope.getPosts();
                });
        }
    };
    $scope.copyData = function(user,title,content,remainder,isActive,isArchive) {
        var data = {
            user:user,
            title:title,
            content:content,
            remainder:remainder,
            isActive:isActive,
            isArchive:isArchive
        };
        //console.log(data);
        $http.post('copyPost', data)
            .success(function(data) {
                //$log.debug(data);
                $scope.getPosts();
            });
    };

    $scope.updateData = function () {
        // use $.param jQuery function to serialize data from JSON
        //alert($scope.data.content);
        //alert($scope.data.reminder);
        var data = {
            id:$scope.data.id,
            user:$scope.data.user,
            title: $scope.data.title,
            content: $scope.data.content,
            reminder:$scope.data.reminder,
            isArchive:0
        };

        $http.post('updatePost', data)
            .success(function(data) {
                $scope.getPosts();
                //console.log("Hello");
            })
            .error(function (data) {
                    console.log(data);
            });
    };

    $scope.del = function (id) {
        // use $.param jQuery function to serialize data from JSON

        //console.log(id);
        var data = {
            id:id
        };
        if(window.confirm("Are you sure?")) {
            $http.post('del', data)
                .success(function (data) {
                    $scope.getPosts();
                });
        }
    };
    $scope.archive = function (id,isArchive) {
        // use $.param jQuery function to serialize data from JSON

        //console.log(id);
        var data = {
            id:id,
            isArchive:isArchive
        };
        if(window.confirm("Are you sure?")) {
            $http.post('archive', data)
                .success(function (data) {
                    $scope.getPosts();
                });
        }
    };


    $scope.data={};
    $scope.addDataToModal = function(user,id,title,content,reminder,isActive,isArchive) {
        //console.log(user,id,title,content);
        //alert(reminder);
        $scope.data.user=user;
        $scope.data.id=id;
        $scope.data.title=title;
        $scope.data.content=content;
        $scope.data.reminder=reminder;
    };

    $scope.updateReminder = function (id) {
        // use $.param jQuery function to serialize data from JSON

        //console.log(id);
        var data = {
            id:id
        };
            $http.post('updateReminder', data)
                .success(function (data) {
                    $scope.getPosts();
                });
        };

    $scope.getPosts = function() {
        $http.get('getPosts')
            .success(function(data) {
                $scope.posts = data;
                var now=new Date();
                var low=new Date();
                var title=null;
                var id=null;
                //console.log($scope.posts);
                for(var p=0;p<($scope.posts).length;p++){
                    var date = new Date(($scope.posts[p]).reminder);
                    //window.alert(date>low);
                    if(($scope.posts[p]).isReminderActive===1){
                        if(date>now) {
                            //window.alert("Fully inside one");
                            if (date > low) {
                                //window.alert("Fully inside one");
                                low = date;
                                title = $scope.posts[p].title;
                                id=$scope.posts[p].id;
                                break;
                            }
                        }
                    }
                }
                for(var p=0;p<($scope.posts).length;p++){
                    var date = new Date(($scope.posts[p]).reminder);
                    if($scope.posts[p].isReminderActive===1) {
                        //window.alert("hello inside");
                        //window.alert(low + "<" + date);
                        //window.alert(low < date);
                        if (date > now) {
                            if (low < date) {

                            }
                            else {
                                low = date;
                                title = $scope.posts[p].title;
                                id=$scope.posts[p].id;
                            }
                            //window.alert(low);
                        }
                    }
                }
                var x=(low.getTime()-now.getTime())/1000;
                //alert(x);
                //var x=(low.getTime()-now.getTime())/1000;
                if(x>0){
                    setTimeout(function(){ alert(title);$scope.updateReminder(id); }, x*1000);
                }
            });
    };
    $scope.getPosts();

    $scope.getArchive = function() {
        $http.get('getArchive')
            .success(function(data) {
                $scope.postsArch = data;
                //console.log(data);
                //$scope.getArchive();
                var now=new Date();
                var low=new Date();
                var title=null;
                var id=null;
                //console.log($scope.posts);
                for(var p=0;p<($scope.postsArch).length;p++){
                    var date = new Date(($scope.postsArch[p]).reminder);
                    //window.alert(date>low);
                    if(($scope.postsArch[p]).isReminderActive===1){
                        if(date>now) {
                            //window.alert("Fully inside one");
                            if (date > low) {
                                //window.alert("Fully inside one");
                                low = date;
                                title = $scope.postsArch[p].title;
                                id=$scope.postsArch[p].id;
                                break;
                            }
                        }
                    }
                }
                for(var p=0;p<($scope.postsArch).length;p++){
                    var date = new Date(($scope.postsArch[p]).reminder);
                    if($scope.postsArch[p].isReminderActive===1) {
                        //window.alert("hello inside");
                        //window.alert(low + "<" + date);
                        //window.alert(low < date);
                        if (date > now) {
                            if (low < date) {

                            }
                            else {
                                low = date;
                                title = $scope.postsArch[p].title;
                                id=$scope.postsArch[p].id;
                            }
                            //window.alert(low);
                        }
                    }
                }
                var x=(low.getTime()-now.getTime())/1000;
                //alert(x);
                //var x=(low.getTime()-now.getTime())/1000;
                if(x>0){
                    setTimeout(function(){ alert(title);$scope.updateReminder(id);$scope.getReminders(); }, x*1000);
                }
            });
    };
    $scope.getArchive();

});


app1.controller("ArchiveHttpGetController", function ($scope, $http) {

    $scope.updateData = function () {
        // use $.param jQuery function to serialize data from JSON

        var data = {
            id:$scope.data.id,
            user:$scope.data.user,
            title: $scope.data.title,
            content: $scope.data.content,
            reminder:$scope.data.reminder,
            isArchive:1
        };

        $http.post('updatePost', data)
            .success(function(data) {
                $scope.getArchive();
                //console.log("Hello");
                $scope.getPosts();
            })
            .error(function (data) {
                //console.log(data);
            });
    };

    $scope.del = function (id) {
        // use $.param jQuery function to serialize data from JSON

        //console.log(id);
        var data = {
            id:id
        };
        if(window.confirm("Are you sure?")) {
            $http.post('del', data)
                .success(function (data) {
                    $scope.getArchive();
                });
        }
    };
    $scope.unArchive = function (id,isArchive) {
        // use $.param jQuery function to serialize data from JSON
        //alert(id+" "+isArchive);
        //console.log(id);
        var data = {
            id:id,
            isArchive:isArchive
        };
        if(window.confirm("Are you sure?")) {
            $http.post('archive', data)
                .success(function (data) {
                    $scope.getArchive();
                });
        }
    };


    $scope.data={};
    $scope.addDataToModal = function(user,id,title,content,reminder,isActive,isArchive) {
        //console.log(user,id,title,content);
        //alert($scope.data.reminder);
        $scope.data.user=user;
        $scope.data.id=id;
        $scope.data.title=title;
        $scope.data.content=content;
        $scope.data.reminder=reminder;

    };


    $scope.getArchive = function() {
        $http.get('getArchive')
            .success(function(data) {
                $scope.postsArch = data;
                //console.log(data);
                //$scope.getArchive();
                var now=new Date();
                var low=new Date();
                var title=null;
                var id=null;
                //console.log($scope.posts);
                for(var p=0;p<($scope.postsArch).length;p++){
                    var date = new Date(($scope.postsArch[p]).reminder);
                    //window.alert(date>low);
                    if(($scope.postsArch[p]).isReminderActive===1){
                        if(date>now) {
                            //window.alert("Fully inside one");
                            if (date > low) {
                                //window.alert("Fully inside one");
                                low = date;
                                title = $scope.postsArch[p].title;
                                id=$scope.postsArch[p].id;
                                break;
                            }
                        }
                    }
                }
                for(var p=0;p<($scope.postsArch).length;p++){
                    var date = new Date(($scope.postsArch[p]).reminder);
                    if($scope.postsArch[p].isReminderActive===1) {
                        //window.alert("hello inside");
                        //window.alert(low + "<" + date);
                        //window.alert(low < date);
                        if (date > now) {
                            if (low < date) {

                            }
                            else {
                                low = date;
                                title = $scope.postsArch[p].title;
                                id=$scope.postsArch[p].id;
                            }
                            //window.alert(low);
                        }
                    }
                }
                var x=(low.getTime()-now.getTime())/1000;
                //alert(x);
                //var x=(low.getTime()-now.getTime())/1000;
                if(x>0){
                    setTimeout(function(){ alert(title);$scope.updateReminder(id);$scope.getReminders(); }, x*1000);
                }
            });
    };
    $scope.getArchive();
    $scope.getPosts = function() {
        $http.get('getPosts')
            .success(function(data) {
                $scope.posts = data;
                var now=new Date();
                var low=new Date();
                var title=null;
                var id=null;
                //console.log($scope.posts);
                for(var p=0;p<($scope.posts).length;p++){
                    var date = new Date(($scope.posts[p]).reminder);
                    //window.alert(date>low);
                    if(($scope.posts[p]).isReminderActive===1){
                        if(date>now) {
                            //window.alert("Fully inside one");
                            if (date > low) {
                                //window.alert("Fully inside one");
                                low = date;
                                title = $scope.posts[p].title;
                                id=$scope.posts[p].id;
                                break;
                            }
                        }
                    }
                }
                for(var p=0;p<($scope.posts).length;p++){
                    var date = new Date(($scope.posts[p]).reminder);
                    if($scope.posts[p].isReminderActive===1) {
                        //window.alert("hello inside");
                        //window.alert(low + "<" + date);
                        //window.alert(low < date);
                        if (date > now) {
                            if (low < date) {

                            }
                            else {
                                low = date;
                                title = $scope.posts[p].title;
                                id=$scope.posts[p].id;
                            }
                            //window.alert(low);
                        }
                    }
                }
                var x=(low.getTime()-now.getTime())/1000;
                //alert(x);
                //var x=(low.getTime()-now.getTime())/1000;
                if(x>0){
                    setTimeout(function(){ alert(title);$scope.updateReminder(id); }, x*1000);
                }
            });
    };
    $scope.getPosts();
});



app2.controller("ReminderHttpGetController", function ($scope, $http) {

    $scope.updateData = function () {
        // use $.param jQuery function to serialize data from JSON

        var data = {
            id:$scope.data.id,
            user:$scope.data.user,
            title: $scope.data.title,
            content: $scope.data.content,
            reminder:$scope.data.reminder,
            isArchive:1
        };

        $http.post('updatePost', data)
            .success(function(data) {
                $scope.getReminders();
                //console.log("Hello");
                $scope.getPosts();
                $scope.getArchive();
            })
            .error(function (data) {
                //console.log(data);
            });
    };

    $scope.del = function (id) {
        // use $.param jQuery function to serialize data from JSON

        //console.log(id);
        var data = {
            id:id
        };
        if(window.confirm("Are you sure?")) {
            $http.post('del', data)
                .success(function (data) {
                    $scope.getReminders();
                });
        }
    };
    $scope.unArchive = function (id,isArchive) {
        // use $.param jQuery function to serialize data from JSON

        //console.log(id);
        var data = {
            id:id,
            isArchive:isArchive
        };
        if(window.confirm("Are you sure?")) {
            $http.post('archive', data)
                .success(function (data) {
                    $scope.getReminders();
                });
        }
    };


    $scope.data={};
    $scope.addDataToModal = function(user,id,title,content,reminder,isActive,isArchive) {
        //console.log(user,id,title,content);
        //alert(reminder);
        $scope.data.user=user;
        $scope.data.id=id;
        $scope.data.title=title;
        $scope.data.content=content;
        $scope.data.reminder=reminder;
    };

    /*$scope.getPosts = function() {
        $http.get('getPosts')
            .success(function(data) {
                $scope.posts = data;
                //console.log(data);
            });
    };
    $scope.getPosts();*/



    $scope.getReminders = function() {
        $http.get('getReminder')
            .success(function(data) {
                $scope.postsReminder = data;
                console.log(data);
                /*var now=new Date();
                var low=new Date();
                var title=null;
                var id=null;
                //console.log($scope.posts);
                for(var p=0;p<($scope.postsReminder).length;p++){
                    var date = new Date(($scope.postsReminder[p]).reminder);
                    //window.alert(date>low);
                    if(($scope.postsReminder[p]).isReminderActive===1){
                        if(date>now) {
                            //window.alert("Fully inside one");
                            if (date > low) {
                                //window.alert("Fully inside one");
                                low = date;
                                title = $scope.postsReminder[p].title;
                                id=$scope.postsReminder[p].id;
                                break;
                            }
                        }
                    }
                }
                for(var p=0;p<($scope.postsReminder).length;p++){
                    var date = new Date(($scope.postsReminder[p]).reminder);
                    if($scope.postsReminder[p].isReminderActive===1) {
                        //window.alert("hello inside");
                        //window.alert(low + "<" + date);
                        //window.alert(low < date);
                        if (date > now) {
                            if (low < date) {

                            }
                            else {
                                low = date;
                                title = $scope.postsReminder[p].title;
                                id=$scope.postsReminder[p].id;
                            }
                            //window.alert(low);
                        }
                    }
                }
                var x=(low.getTime()-now.getTime())/1000;
                //alert(x);
                //var x=(low.getTime()-now.getTime())/1000;
                if(x>0){
                    setTimeout(function(){ alert(title);$scope.updateReminder(id);$scope.getReminders(); }, x*1000);
                }
            })
        .error(function (data) {
            console.log(data);*/
        });
    };
    $scope.getReminders();


    $scope.getPosts = function() {
        $http.get('getPosts')
            .success(function(data) {
                $scope.posts = data;
                var now=new Date();
                var low=new Date();
                var title=null;
                var id=null;
                //console.log($scope.posts);
                for(var p=0;p<($scope.posts).length;p++){
                    var date = new Date(($scope.posts[p]).reminder);
                    //window.alert(date>low);
                    if(($scope.posts[p]).isReminderActive===1){
                        if(date>now) {
                            //window.alert("Fully inside one");
                            if (date > low) {
                                //window.alert("Fully inside one");
                                low = date;
                                title = $scope.posts[p].title;
                                id=$scope.posts[p].id;
                                break;
                            }
                        }
                    }
                }
                for(var p=0;p<($scope.posts).length;p++){
                    var date = new Date(($scope.posts[p]).reminder);
                    if($scope.posts[p].isReminderActive===1) {
                        //window.alert("hello inside");
                        //window.alert(low + "<" + date);
                        //window.alert(low < date);
                        if (date > now) {
                            if (low < date) {

                            }
                            else {
                                low = date;
                                title = $scope.posts[p].title;
                                id=$scope.posts[p].id;
                            }
                            //window.alert(low);
                        }
                    }
                }
                var x=(low.getTime()-now.getTime())/1000;
                //alert(x);
                //var x=(low.getTime()-now.getTime())/1000;
                if(x>0){
                    setTimeout(function(){ alert(title); }, x*1000);
                }
            });
    };
    $scope.getPosts();

    $scope.getArchive = function() {
        $http.get('getArchive')
            .success(function(data) {
                $scope.postsArch = data;
                //console.log(data);
                //$scope.getArchive();
                var now=new Date();
                var low=new Date();
                var title=null;
                var id=null;
                //console.log($scope.posts);
                for(var p=0;p<($scope.postsArch).length;p++){
                    var date = new Date(($scope.postsArch[p]).reminder);
                    //window.alert(date>low);
                    if(($scope.postsArch[p]).isReminderActive===1){
                        if(date>now) {
                            //window.alert("Fully inside one");
                            if (date > low) {
                                //window.alert("Fully inside one");
                                low = date;
                                title = $scope.postsArch[p].title;
                                id=$scope.postsArch[p].id;
                                break;
                            }
                        }
                    }
                }
                for(var p=0;p<($scope.postsArch).length;p++){
                    var date = new Date(($scope.postsArch[p]).reminder);
                    if($scope.postsArch[p].isReminderActive===1) {
                        //window.alert("hello inside");
                        //window.alert(low + "<" + date);
                        //window.alert(low < date);
                        if (date > now) {
                            if (low < date) {

                            }
                            else {
                                low = date;
                                title = $scope.postsArch[p].title;
                                id=$scope.postsArch[p].id;
                            }
                            //window.alert(low);
                        }
                    }
                }
                var x=(low.getTime()-now.getTime())/1000;
                //alert(x);
                //var x=(low.getTime()-now.getTime())/1000;
                if(x>0){
                    setTimeout(function(){ alert(title);$scope.updateReminder(id);$scope.getReminders(); }, x*1000);
                }
            });
    };
    $scope.getArchive();




});


