$(function(){
  let tag = [0,0,1,1];
  let bit = [0];
  function setCss(className,num){
    if(num.every((i)=>{return i==1})){
      $(className).removeAttr('disabled');
      $(className).css('backgroundColor','#c81623');
    }else{
      $(className).attr('disabled','disabled');
      $(className).css('backgroundColor','#ccc');
    }
  }
  setCss('.insert',tag);
  setCss('.delete',bit);
  function wrong(e){
    $(e).siblings('span').eq(0).css("display","inline");
    $(e).siblings('span').eq(1).css("display","none");
  }
  function right(e){
    $(e).siblings('span').eq(0).css("display","none");
    $(e).siblings('span').eq(1).css("display","inline")
  }
  $('#username1').on('blur',function(e){
    const username = e.target.value.trim();
    if(username.length>10 || username.length<=0){
      tag[0] = 0;
      setCss('.insert',tag);
      wrong(this);
    }
    else{
      tag[0] = 1;
      setCss('.insert',tag);
      right(this);
    }
  });
  $('#name').on('blur',function(e){
    const name = e.target.value.trim();
    if(name.length>20 || name.length<=0){
      tag[1] = 0;
      setCss('.insert',tag);
      wrong(this);
    }
    else{
      tag[1] = 1;
      setCss('.insert',tag);
      right(this);
    }
  });
  $('#age').on('blur',function(e){
    const age = parseInt(e.target.value);
    if(age<0){
      tag[2] = 0;
      setCss('.insert',tag);
      wrong(this);
    }
    else{
      tag[2] = 1;
      setCss('.insert',tag);
      right(this);
    }
  });
  $('#telenumber').on('blur',function(e){
    const reg = /^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
    const telenumber = e.target.value.replace(/\s*/g,"");
    $(this).val(telenumber);
    let flag = reg.test(telenumber);
    if(telenumber.length==0) flag=true;
    if(!flag){
      tag[3] = 0;
      setCss('.insert',tag);
      wrong(this);
    }
    else{
      tag[3] = 1;
      setCss('.insert',tag);
      right(this);
    }
  });

  $('#username2').on('blur',function(e){
    const username = e.target.value.trim();
    if(username.length>10 || username.length<=0){
      bit[0] = 0;
      setCss('.delete',bit);
      wrong(this);
    }
    else{
      bit[0] = 1;
      setCss('.delete',bit);
      right(this);
    }
  });

  $('.insert').on('click',function(){
    const person = $('#insertForm').serialize().split('&');
    person.forEach((val,i,arr)=>{
      let p = `<p>${decodeURI(val)}</p>`;
      $('.modal-body1').append(p);
    });
  });
  $('.delete').on('click',function(){
    const user = $('#deleteForm').serialize().split('&');
    console.log(user);
    user.forEach((val,i,arr)=>{
      let p = `<p>${decodeURI(val)}</p>`;
      $('.modal-body2').append(p);
    });
  })
})