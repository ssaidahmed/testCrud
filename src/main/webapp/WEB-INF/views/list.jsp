<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>

<%@ page session="false" %>
<html>
<head>
    <%--jqGrid--%>

        <link rel="stylesheet" type="text/css" media="screen" href="\resource\jquery-ui-1.12.0.custom\jquery-ui.theme.min.css" />

        <link rel="stylesheet" type="text/css" media="screen" href="\resource\Guriddo_jqGrid\css\ui.jqgrid.css" />


        <script src="\resource\Guriddo_jqGrid\js\jquery-1.11.0.min.js" type="text/javascript"></script>
        <script src="\resource\jquery-ui-1.12.0.custom\jquery-ui.min.js" type="text/javascript"></script>
        <script src="\resource\Guriddo_jqGrid\js\i18n\grid.locale-ru.js" type="text/javascript"></script>
        <script src="\resource\Guriddo_jqGrid\js\jquery.jqGrid.min.js" type="text/javascript"></script>


        <style type="text/css">
            .tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
            .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
            .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
            .tg .tg-4eph{background-color:#f9f9f9}
        </style>
        <style>
            .ui-jqgrid .ui-pg-input {height:20px;}
            .ui-jqgrid .ui-jqgrid-pager {height:40px;}
            .ui-jqgrid .ui-jqgrid-pager .ui-pg-div {font-size:14px;}
        </style>

        <script type="text/javascript">

            $(function () {

                $("#list").jqGrid({
                    url:'/listgrid',
                    datatype:'json',
                    mtype:'GET',
                    colNames:['User ID','User Name','User Age','Is Admin','Create Date' ],
                    colModel:[
                        {name: 'id',
                            index: 'id',
                            width:150,
                            search:true

                        },
                        {name:'name', index:'name',
                            width:150,
                            formatter:'text',search:true, editable: true

                        },
                        {name:'age', index:'age', width:150, editrules:{ custom:true, custom_func:function (value, age) {
                            if (value < 10 || value >110)
                                return [false,"Введите число в диапазоне 10 - 110"];
                            else
                                return [true,""];
                        }, integer:true},formatter:'integer', editable: true},
                        {name:'isAdmin', index:'isAdmin', formatter:'checkbox', width:150, editable: true, edittype:"checkbox"},
                        {name:'createDateString',editable:true, editrules:{edithidden:false, required:true, date:true}, index:'createDate',formatter:'data', width:150}
                    ],
                    jsonReader: {
                        root: "userData",
                        page: "currentPage",
                        total: "totalPages",
                        records: "totalRecords",
                        repeatitems: false,
                        id: "id"
                    },
                    pager: '#pager',
                    rowNum: 10,
//                    rowList: [10,20,30],
                    sortname: 'name',
                    sortorder: 'asc',
                    viewrecords: true,
                    rownumbers: false,
                    gridview: true,
                    height: 'auto',
                    width:1200,
//                    loadonce: true,
                    caption: 'User List'

                });

                jQuery("#list").jqGrid('navGrid','#pager', {
                            edit: true, // редактирование
                            add: true, // добавление
                            del: true, // удаление
                            search: false,
                            view: true,
                            refresh: false,
                            searchtext: "Поиск",
                            viewtext: "Смотреть",
                            viewtitle: "Выбранная запись",
                            addtext: "Добавить",
                            edittext: "Изменить",
                            deltext: "Удалить"


                },
                        update("edit"),
                        update("add"),

                        update("del")

                );
                function update(act) {
                    return {
                        closeAfterAdd: true, // закрыть после добавления
                        height: 250,
                        width: 400,
                        closeAfterEdit: true, // закрыть после редактирования
                        reloadAfterSubmit: true, // обновление
                        drag: true,
                        onclickSubmit: function (params) {
                            var list = $("#list");
                            var selectedRow = list.getGridParam("selrow");
                            rowData = list.getRowData(selectedRow);

                            if (act === "add") {

                                params.url = '/user/add';
                            }
                            else if (act === "del") {

                                params.url = '/remove/' + rowData.id;
                            }
                            else if (act === "edit") {

                                params.url = '/editpost/' + rowData.id;
                            }
                        },
                        afterSubmit: function (response, postdata) {
                            // обновление грида
                            $(this).jqGrid('setGridParam', { datatype: 'json' }).trigger('reloadGrid');
                            return [true, "", 0]
                        }
                    };
                }


                $("#list").trigger("reloadGrid", { fromServer: true, page: 1 });


            });



        </script>
</head>
<body>

<div id="mysearch" align="top-center">

    <input type="text"  id="userName" size="20"/>
    <input type="button" onclick="getValue()" value="найти по имени" id="submit">
    <script type="text/javascript">

        function getValue() {
            var textSearch = document.getElementById("userName").value;
            document.location.href ="/search/" + textSearch;
        }
    </script>

    <div>
        <c:if test="${!empty search}">
            <table class="tg" id="tableSearch">
                <tr>
                    <th width="80">User ID</th>
                    <th width="100">Name</th>
                    <th width="80">Age</th>
                    <th width="80">is Admin</th>
                    <th width="150">Create Date</th>
                </tr>
                <tr>
                    <td>${search.id}</td>
                    <td>${search.name}</td>
                    <td>${search.age}</td>
                    <td>${search.isAdmin}</td>
                    <td>${search.createDateString}</td>
                </tr>
            </table>
        </c:if>
    </div>
</div>

<br>
<h1>User Table</h1>
<div id="pager"></div>
<table id="list" align="center"><tr><td></td></tr></table>

</body>
</html>

