$(function(){
    const ajaxUrl = 'servlet';
    $('#delete').on('click',function(){
        const s = $('#deleteForm').serialize().substr(9);
        $.ajax({
            type: 'post',
            url: ajaxUrl,
            data: $('#deleteForm').serialize(),
            dataType: 'json',
            success: function (response){
                localStorage.setItem('res',JSON.stringify(response));
                localStorage.setItem('status','200');
                localStorage.setItem('time',(new Date()+''));
                window.location.href = 'result.html';
            },
            error: function (xhr){
                localStorage.setItem('res',"{"+"\"operator\""+":"+"\""+"删除"+"\""+","+"\"table\""+":"+"\""+"user"+"\""+","+"\"username\""+":"+"\""+s+"\""+"}");
                localStorage.setItem('status',xhr.status+'');
                localStorage.setItem('time',(new Date()+''));
                window.location.href = 'result.html';
            }
        });
    });
    $('#insert').on('click',function (){
        const s = $('#insertForm').serialize().split('&');
        const s1 = s[0].substr(9);
        $.ajax({
            type: 'post',
            url: ajaxUrl,
            data: $('#insertForm').serialize(),
            dataType: 'json',
            success: function (response){
                localStorage.setItem('res',JSON.stringify(response));
                localStorage.setItem('status','200');
                localStorage.setItem('time',(new Date()+''));
                let e = new Date()+'';
                window.location.href = 'result.html';
            },
            error: function (xhr){
                localStorage.setItem('res',"{"+"\"operator\""+":"+"\""+"插入/更新"+"\""+","+"\"table\""+":"+"\""+"person"+"\""+","+"\"username\""+":"+"\""+s1+"\""+"}");
                localStorage.setItem('status',xhr.status+'');
                localStorage.setItem('time',(new Date()+''));
                window.location.href = 'result.html';
            }
        });
    });
})
