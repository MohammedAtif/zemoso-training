search.controller("searchHttpGetController",function ($scope,$http) {
    var posArray={};
    var length;
    var x;
    var timeOut;
    $scope.del = function (id) {
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
    $scope.data={};
    $scope.addDataToModal = function(user,id,title,content,reminder,isActive,isArchive) {
        //console.log(user,id,title,content);
        //alert(reminder);
        $scope.data.user=user;
        $scope.data.id=id;
        $scope.data.title=title;
        $scope.data.content=content;
        $scope.data.reminder=reminder;
        $scope.data.isArchive=isArchive;
        alert(isArchive);
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
            isArchive:$scope.data.isArchive
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
        $http.get('getPostsAll')
            .success(function(data) {
                // console.log($scope.posts);
                $scope.posts = data;
                console.log($scope.posts);
                var now=new Date();
                var low=new Date();
                var title=null;
                var id=null;
                for(var i=0;i<($scope.posts).length;i++){
                    posArray[i]=$scope.posts[i].id;
                }
                //console.log(posArray);
                length=($scope.posts).length;
                //console.log($scope.posts);
                for(var p=0;p<($scope.posts).length;p++){
                    var date = new Date(($scope.posts[p]).reminder);
                    //window.alert(date>low);
                    if(($scope.posts[p]).isReminderActive===1){
                        if(date>now) {
                            if (date > low) {
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
                        if (date > now) {
                            if (low < date) {

                            }
                            else {
                                low = date;
                                title = $scope.posts[p].title;
                                id=$scope.posts[p].id;
                            }
                        }
                    }
                }
                x=(low.getTime()-now.getTime())/1000;
                clearTimeout(timeOut);
                //alert(x);
                //var x=(low.getTime()-now.getTime())/1000;
                if(x>0){
                    timeOut=setTimeout(function(){ alert(title);$scope.updateReminder(id); }, x*1000);
                }
            });
    };
    $scope.getPosts();
    $scope.search = function (row) {
        return (angular.lowercase(row.title).indexOf(angular.lowercase($scope.query) || '') !== -1 ||
        angular.lowercase(row.content).indexOf(angular.lowercase($scope.query) || '') !== -1);
    };
});
login.controller("loginHttpGetController", function ($scope, $http, $location) {
    $scope.login = function() {
        var data = {
            userEmail: $scope.email,
            userPassword: $scope.password
        };
        console.log(data);
        $http.post('/', data)
            .success(function (data) {
                location.href = '/dash';
            })
            .error(function (data) {
                //alert("error");
                $scope.alertMessage=data;
            })
    };
});

signup.controller("SignupHttpGetController", function ($scope, $http) {
    $scope.signup = function() {
        var regex = /^[a-zA-Z ]{2,30}$/;
        if (!(regex.test($scope.userName))) {
            $scope.alertMessage="Name should not contain numbers or special characters!";
        }
        else {

            var data = {
                userName: $scope.userName,
                userEmail: $scope.userEmail,
                userPassword: $scope.userPassword
            };
            console.log(data);
            $http.post('regis', data)
                .success(function (data) {
                    location.href = '/dash';
                })
                .error(function (data) {
                    $scope.alertMessage = data;
                })
        }
    };

});
app.controller("HttpGetController", function ($scope, $http) {
    $scope.blurUpdate = function() {
        $scope.showRest=false;
    };
    var posArray={};
    var length;
    $scope.updatePosition=function (oldPos,newPos) {
        var temp=posArray[newPos];
        posArray[newPos]=posArray[oldPos];
        if(oldPos<newPos) {
            while (newPos > 0) {
                var t = posArray[--newPos];
                posArray[newPos] = temp;
                temp = t;
            }
        }
        else if(oldPos>newPos){
            while (newPos < oldPos) {
                var t = posArray[++newPos];
                posArray[newPos] = temp;
                temp = t;
            }
        }
        console.log(posArray);
        var pos="";
        var ids="";
        for(var i=0;i<length;i++){
            //alert("in");
            pos+= i.toString()+' ';
            ids+= posArray[i].toString()+" ";
        }
        //alert(ids);
        var data ={
            positions:pos,
            ids:ids
        };
        $http.post('updatePositions', data)
            .success(function (data) {
                //$log.debug(data);
                $scope.getPosts();
            });
    }



    $scope.sendData = function () {
        // use $.param jQuery function to serialize data from JSON
        if(!($scope.title==undefined && $scope.content==undefined)) {
            //alert($scope.color);
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
            delete $scope.title;
            delete $scope.content;
            delete $scope.reminderTime;
        }
    };
    $scope.sortableOptions = {
        containment: '.row',
        onDrop: function (event) {
            console.log("orderChanged()");
            console.dir(event);
            alert("helo");
        }
    }

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
var x;
    var timeOut;
    $scope.getPosts = function() {
        $http.get('getPosts')
            .success(function(data) {
                $scope.posts = data;
                var now=new Date();
                var low=new Date();
                var title=null;
                var id=null;
                for(var i=0;i<($scope.posts).length;i++){
                    posArray[i]=$scope.posts[i].id;
                }
                //console.log(posArray);
                length=($scope.posts).length;
                //console.log($scope.posts);
                for(var p=0;p<($scope.posts).length;p++){
                    var date = new Date(($scope.posts[p]).reminder);
                    //window.alert(date>low);
                    if(($scope.posts[p]).isReminderActive===1){
                        if(date>now) {
                            if (date > low) {
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
                        if (date > now) {
                            if (low < date) {

                            }
                            else {
                                low = date;
                                title = $scope.posts[p].title;
                                id=$scope.posts[p].id;
                            }
                        }
                    }
                }
                x=(low.getTime()-now.getTime())/1000;
                clearTimeout(timeOut);
                //alert(x);
                //var x=(low.getTime()-now.getTime())/1000;
                if(x>0){
                    timeOut=setTimeout(function(){ alert(title);$scope.updateReminder(id); }, x*1000);
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
            isArchive:$scope.data.isArchive
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
        $scope.data.isArchive=isArchive;
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


