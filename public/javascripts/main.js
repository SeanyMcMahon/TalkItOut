/**
 * Created by iloveyou on 7/1/14.
 */

$(document).ready(function(){

    $(".tabs1").tabs();

    $(".problem-name").on("click", function(){
        var data = $(this).data("problem-name");
        searchForMatchingData(data);
    });

    function searchForMatchingData(data){
        $(".problem-group").each(function(){

            if($(this).data("problem-group")== data){
                $(this).fadeIn("slow");
                $(".tabs1").tabs();

            }else{
                $(this).hide();

            }

        });

        $(".tab").hover(
            function(){
            var tabNumber = $(this).data("tab");
            $(".tip" + tabNumber).show();
        },

            function(){
                var tabNumber = $(this).data("tab");
                $(".tip" + tabNumber).hide();

            }
        );




        var num = 0;
        var whatName=0;

        $(".conversation").keydown(function(e){

            //40 is the arrow down key ASCI number
            if(e.which==40) {

                num++;
                if (num == 2) {

                    var initialText = $(this).val();

                    if(whatName == 1) {
                        $(this).val(initialText + "\n" + "Socrates: ");
                        num = 0;
                        whatName = 0;
                     }else{
                        var name = $(".nameText").text();
                        $(this).val(initialText + "\n" + name + ": ");
                        whatName= 1;
                        num= 0;

                    }

                }
            }else{

                num= 0;
            }

        });

        //TODO finish ajax when I have access to documentation


        $(".save").on("click", function(){


          var data = $(this).data("id");

          var name = searchForInputArea(".name" ,data);
          var description = searchForInputArea(".description" ,data);
          var conversation = searchForInputArea(".conversation" ,data);
          var resources = searchForInputArea(".resources" ,data);
          var solution = searchForInputArea(".solution" ,data);


            alert(name + " " + description + conversation + resources + solution);

          var route = playRouter.controllers.Application.updateProblem()
            $.ajax(route.url(),{
                type: r.type,
                data:{"id":data,"name":name,"description":description, "conversation":conversation, "resources":resources, "solution":solution },
                success:function(){
                    alert('done');
                },
                error:function(err){
                    alert(err + "gone wrong");
                }


            })
        });


        function searchForInputArea(name, data){

            var value = null;
            $(name).each(function(){
               if($(this).data("id") == data){
                   value = $(this).val();
               }
            });

            return value;
        }



    }


});