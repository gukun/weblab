/*
Copyright (c) 2003-2010, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config ) {
	config.language = 'zh-cn';

	if(config.ckfinderPath){
		config.filebrowserBrowseUrl = config.ckfinderPath+'/ckfinder.html?type=files&start=files:'+config.ckfinderUploadPath;
		config.filebrowserImageBrowseUrl = config.ckfinderPath+'/ckfinder.html?type=images&start=images:'+config.ckfinderUploadPath;
		config.filebrowserFlashBrowseUrl = config.ckfinderPath+'/ckfinder.html?type=flash&start=flash:'+config.ckfinderUploadPath;
		config.filebrowserUploadUrl = config.ckfinderPath+'/core/connector/java/connector.java?command=QuickUpload&type=files&currentFolder='+config.ckfinderUploadPath;
		config.filebrowserImageUploadUrl = config.ckfinderPath+'/core/connector/java/connector.java?command=QuickUpload&type=images&currentFolder='+config.ckfinderUploadPath;
		config.filebrowserFlashUploadUrl = config.ckfinderPath+'/core/connector/java/connector.java?command=QuickUpload&type=flash&currentFolder='+config.ckfinderUploadPath;
	}

};
/*CKEDITOR.stylesSet.add( 'default', [
	/!* Block Styles *!/
	{ name : '首行缩进'	, element : 'p', styles : { 'text-indent' : '20pt' } },
	/!* Inline Styles *!/
	{ name : '标注黄色'	, element : 'span', styles : { 'background-color' : 'Yellow' } },
	{ name : '标注绿色'	, element : 'span', styles : { 'background-color' : 'Lime' } },
	/!* Object Styles *!/
	{ name : '图片左对齐', element : 'img', attributes : { 'style' : 'padding: 5px; margin-right: 5px', 'border' : '2', 'align' : 'left' } },
	{ name : '图片有对齐', element : 'img', attributes : { 'style' : 'padding: 5px; margin-left: 5px', 'border' : '2', 'align' : 'right' } },
	{ name : '无边界表格', element : 'table', styles: { 'border-style': 'hidden', 'background-color' : '#E6E6FA' } }
]);*/
