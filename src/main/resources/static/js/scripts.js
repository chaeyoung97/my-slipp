$(".answer-write input[type=submit]").click(addAnswer); //클릭 이벤트발생시점에 addAnswer함수를 호출해라

function addAnswer(e){
  console.log("click");
  e.preventDefault(); //클릭이벤트 발생시 디폴트 동작을 막아버린다. 즉 서버로 데이터가 바로 전송안되게 해버린것이다.

  var queryString = $(".answer-write").serialize(); //질문 스트링 값 가져오기
  console.log("query: " + queryString); //콘솔에 찍어보기

  var url = $(".answer-write").attr("action");  //action속성 값을 가져옴
  console.log("action : "+ url);

  //이제 서버에 데이터 전송해야하는데 $.ajax 함수를 이용하면 됨
  $.ajax({
      type : 'post',    //type:http메서드
      url : url ,
      data : queryString,
      error : onError, //응답결과가 에러발생했을 때 이 함수를 실행하겠다
      success : onSuccess   //정상적으로 db에 답변추가될 때 실행될 함수
  });
}

function onError(){

}

function  onSuccess(data, status){
    console.log(data);

   var answerTemplate = $("#answerTemplate").html();
   var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id, data.id)
    $(".qna-comment-slipp-articles").append(template);

   $(".answer-write textarea").val("");
}

$(".link-delete-article").click(deleteAnswer); //삭제 이벤트 디폴트 막기
function deleteAnswer(e){
    e.preventDefault();

    var deleteBtn = $(this);
    console.log(1);
    var url = deleteBtn.attr("href");
    console.log("url : " + url);

    $.ajax({
        type : 'delete',
        url : url,
        dataType : 'json',
        error : onError,
        success : onSuccess
    });

    function onError(){
        console.log("error");
    }

    function  onSuccess(data, status){
        console.log("success")
        console.log(data);
        if(data.vaild){
            deleteBtn.closest("article").remove();
        }else{
            alert(data.errorMessage);
        }
    }

}
$(document).on('click', '.link-delete-article', deleteAnswer);

String.prototype.format = function() {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) {
        return typeof args[number] != 'undefined' ? args[number] : match;
    });
};
/*
form태그로 접근 한 다음 그 자식의 submit버튼이 클릭되는 시점을 잡아볼 것임
 */

/*
String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};
*/