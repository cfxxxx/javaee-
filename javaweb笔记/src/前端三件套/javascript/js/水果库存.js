window.onload=function(){
    //当页面加载完成，我们需要绑定各种事件

    updateZJ();
    //根据id获取到表格
    var fruitTbl = document.getElementById("tbl_fruit");
    var rows = fruitTbl.rows;
    for(var i = 0; i < rows.length-1 ; i++){
        var tr = rows[i];
        myBindEvent(tr);
    }

    var addFruitTbl = document.getElementById("add_fruit_tbl");
    var addRows = addFruitTbl.rows;
    for(var i = 0; i < addRows.length-1; i++){
        var tr = addRows[i];
        var addInput = tr.cells[1].firstChild;
        if(i != 0){
            addInput.onkeydown = checkInput;
        }
    }

    var addButton = document.getElementById("btnAdd");
    var resetButton = document.getElementById("btnReset");
    addButton.onclick = addRow;
}

function myBindEvent(tr){
//1.绑定鼠标悬浮设置背景颜色事件
        tr.onmouseover = showBGColor;
        //2.绑定鼠标离开回复原始样式事件
        tr.onmouseout = clearBGColor;

        //3.绑定鼠标悬浮在单价单元格时显示手势事件
        var cells = tr.cells;
        var priceTD = cells[1];
        priceTD.onmouseover = showHand;
        //4.绑定鼠标点击单价单元格的事件
        priceTD.onclick = editPrice;
        //8.绑定删除小图标的点击事件
        var img = cells[4].firstChild;
        if(img && img.tagName=="IMG"){
            img.onclick = delFruit;
        }
}

function addRow(){
    var fname = document.getElementById("fname").value;
    var price = parseInt(document.getElementById("price").value);
    var fcount = parseInt(document.getElementById("fcount").value);
    var xj = price * fcount;

    var tr = document.getElementById("tbl_fruit").insertRow(document.getElementById("tbl_fruit").rows.length-1);
    var fnameTd = tr.insertCell();
    fnameTd.innerText = fname;
    var priceTd = tr.insertCell();
    priceTd.innerText = price;
    var fcountTd = tr.insertCell();
    fcountTd.innerText = fcount;
    var xjTd = tr.insertCell();
    xjTd.innerText = xj;
    var imgTd = tr.insertCell();
    imgTd.innerHTML = "<img src='imgs/delete.jpg' class='delImg'>";
    myBindEvent(tr);

    updateZJ();
}
//单点击图标时，删除指定行
function delFruit(){
    if(event && event.srcElement && event.srcElement.tagName=="IMG"){
        //alert表示弹出一个对话框，只有确定按钮
        //confirm表示弹出一个对话框，有确定和取消按钮。当点击确定，返回true，否则返回false
        if(window.confirm("是否确认删除当前库存")){
            var img = event.srcElement;
            var tr = img.parentElement.parentElement;
            var fruitTbl = document.getElementById("tbl_fruit");
            fruitTbl.deleteRow(tr.rowIndex);
            updateZJ();
        }
    }
}

//当鼠标悬浮时，显示背景颜色
function showBGColor(){
    //event：当前发生的事件
    //event.srcElement：事件源
    //alert(event.srcElement);
    //alert(event.srcElement.tagName); --> TD
    if(event && event.srcElement && event.srcElement.tagName=="TD"){
        var td = event.srcElement;
        //td.parentElement 表示获取td的父元素 --> TR
        var tr = td.parentElement;
        //如果想要通过js代码设置某节点的样式，则需要加上tr.style
        tr.style.backgroundColor = "navy";
        //tr.cells表示获取这个tr中的所有单元格
        var tds = tr.cells;
        for(var i = 0; i < tds.length ; i++){
            tds[i].style.color = "white";
        }
    }
}

//当鼠标离开的时候，回复原始样式
function clearBGColor(){
    if(event && event.srcElement && event.srcElement.tagName=="TD"){
        var td = event.srcElement;
        var tr = td.parentElement;
        tr.style.backgroundColor="transparent";
        var tds = tr.cells;
        for(var i = 0; i < tds.length ; i++){
            tds[i].style.color="threeddarkshadow";
        }
    }
}

//当鼠标悬浮在单价单元格时，显示手势
function showHand(){
    if(event && event.srcElement && event.srcElement.tagName=="TD"){
        var td = event.srcElement;
        //cursor：光标
        td.style.cursor = "hand"
    }
}

//当鼠标单击单价单元格时，进行价格编辑
function editPrice(){
    if(event && event.srcElement && event.srcElement.tagName=="TD"){
        var priceTD = event.srcElement;

        //判断当前priceTD有子节点，而且第一个子节点是文本节点，TextNode对应的是3，ElementNode对应的是1
        if(priceTD.firstChild && priceTD.firstChild.nodeType == 3){
             //innerText 表示设置或者获取当前节点的内部文本
             var oldPrice = priceTD.innerText;
             //innerHTML 表示设置当前节点的内部HTML
             priceTD.innerHTML = "<input type='text' size='4'>";

             var input = priceTD.firstChild;
             if(input.tagName == "INPUT"){
                 input.value = oldPrice;
                 //选中输入框内部的文本
                 input.select();
                 //5.绑定输入框失去焦点事件，失去焦点，更新单价
                 input.onblur = updatePrice;
                 //9.在输入框商绑定键盘摁下的时间，此处我需要保证用户输入的是数字
                 input.onkeydown = checkInput;
             }
        }
    }
}

//检验键盘摁下的值的方法
function checkInput(){
    //0~9：48~57
    //backspace：8
    //enter：13
    var kc = event.keyCode;
    /*console.log(kc);*/
    if(!((kc>=48 && kc<=57) || kc==8 || kc==13)){
        event.returnValue=false;
    }
    if(kc == 13){
        event.srcElement.blur();
    }
}

function updatePrice(){
    if(event && event.srcElement && event.srcElement.tagName == "INPUT"){
        var input = event.srcElement;
        var newPrice = input.value;
        var priceTD = input.parentElement;
        priceTD.innerText = newPrice;

        //6.更新当前行的小计这一个格子的值
        updateXJ(priceTD.parentElement);
    }
}

//更新指定行的小计
function updateXJ(tr){
    if(tr && tr.tagName=="TR"){
        var tds = tr.cells;
        //innerText获取到的值的类型是字符串类型，因此需要类型转换，才能进行数学运算
        tds[3].innerText = parseInt(tds[1].innerText)*parseInt(tds[2].innerText);

        //7.更新总计
        updateZJ();
    }
}

//更新总计
function updateZJ(){
    var fruitTbl = document.getElementById("tbl_fruit");
    var rows = fruitTbl.rows;
    var sum = 0;
    for(var i = 1; i < rows.length - 1; i++){
        sum = sum + parseInt((rows[i].cells)[3].innerText);
    }
    (rows[rows.length-1].cells)[1].innerText = sum;
}