app.controller("HttpGetController", function ($scope, $http) {

    $scope.sendData = function () {
        // use $.param jQuery function to serialize data from JSON
        //window.alert($scope.reminderTime);

        var data = {
            user:$scope.user,
            title: $scope.title,
            content: $scope.content,
            reminder: $scope.reminderTime
        };

        $http.post('addPost', data)
            .success(function(data) {
                //$log.debug(data);
                $scope.getPosts();
            });
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
        console.log(data);
        $http.post('copyPost', data)
            .success(function(data) {
                //$log.debug(data);
                $scope.getPosts();
            });
    };

    $scope.updateData = function () {
        // use $.param jQuery function to serialize data from JSON
        //console.log($scope.data.id);
        var data = {
            id:$scope.data.id,
            user:$scope.data.user,
            title: $scope.data.title,
            content: $scope.data.content,
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

        console.log(id);
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
        $scope.data.user=user;
        $scope.data.id=id;
        $scope.data.title=title;
        $scope.data.content=content;
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
                console.log($scope.posts);
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


app1.controller("ArchiveHttpGetController", function ($scope, $http) {

    $scope.updateData = function () {
        // use $.param jQuery function to serialize data from JSON

        var data = {
            id:$scope.data.id,
            user:$scope.data.user,
            title: $scope.data.title,
            content: $scope.data.content,
            isArchive:1
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
                    $scope.getPosts();
                });
        }
    };


    $scope.data={};
    $scope.addDataToModal = function(user,id,title,content,reminder,isActive,isArchive) {
        //console.log(user,id,title,content);
        $scope.data.user=user;
        $scope.data.id=id;
        $scope.data.title=title;
        $scope.data.content=content;
    };


    $scope.getPosts = function() {
        $http.get('getPosts')
            .success(function(data) {
                $scope.posts = data;
                //console.log(data);
            });
    };
    $scope.getPosts();



    $scope.getArchive = function() {
        $http.get('getArchive')
            .success(function(data) {
                $scope.postsArch = data;
                //console.log(data);
                $scope.getArchive();
            });
    };
    $scope.getArchive();
});


