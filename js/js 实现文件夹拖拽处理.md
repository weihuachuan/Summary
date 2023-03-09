# js 实现文件夹拖拽处理

```js
(function(event) {
	let FileUploadDP = { //默认参数
		ClickElement: null, //单击元素
		DragElement: null, //拖拽元素
		OPPattern: null, //操作模式
		ReturnButtom: null, //返回元素
		QuantityLimit: 1024, //数量限制
		ConsoleAutograph: true, //是否开启版权提示
		OutputWarning: true, //是否输出警告
		FormatList: ['apng', 'svg+xml', 'avif', 'bmp', 'gif', 'ico', 'cur', 'jpg', 'jpeg', 'jfif', 'pjpeg',
			'pjp', 'png',
			'svg', 'tif', 'tiff', 'webp', 'ogg', 'mp3', 'wav', 'flac', 'mpeg', 'mp4', 'webm', 'avi', 'ogv'
		],
		OPState: function(OPState) {
			console.log("OPState:" + OPState);
		}, //操作状态
		ResultSet: function(ResultSet) {
			console.log(ResultSet);
		} //操作结果
	};
 
	let ClearSetTimeout = null;
 
	function TemporaryListObject(data) {
		//创建临时列表对象（自定构造函数创建对象）
		//https://www.jb51.net/article/230503.htm
	}
 
	let TemporaryList = new TemporaryListObject(null);
 
	function ResultObject(data) {
		//创建结果集对象（自定构造函数创建对象）
		//https://www.jb51.net/article/230503.htm
		//创建常用键值对
		this.ResultType = null;
		this.UploadList = new Object();
		this.AttributeList = new Object();
	}
 
	let getResult = new ResultObject(null);
 
	//用于判断是否点击过“提交”一类按钮
	let Whether = false;
 
	//文件上传类
	class FileUploadClass {
		constructor(data) {
			if (!navigator.onLine) {
				console.warn('设备未联网，可能发生操作失败');
			}
			if (!window.location.protocol || window.location.protocol === 'file:') {
				throw new Error('网络协议不正确，至少为http协议');
			}
			console.log("%c版本号：v1.0.01",
				"border-radius: 3px;padding:1.2px 3px 1px 3px;background-color:#fe7300;color:#FFFFFF;font-weight: bold;"
			);
		}
 
		// 初始化
		init(options) {
			//将this赋值给ClassThis，以便后续调用，防止与其他的this混淆
			//class的函数（第一层）调用是没问题的，因为它们的this就是class的this
			//但它们中的函数调用就不一定行了，因为this不一定是class的this
			const ClassThis = this;
 
			//将接收的参数赋值给this.options
			this.options = Object.assign({}, FileUploadDP, options);
 
			//返回状态码
			this.options.OPState(0);
 
			//判断是否开启版权提示
			if (this.options.ConsoleAutograph === true) {
				console.log(
					"%cFileUpload.js文件由“编写者”博客编写\n博客地址：https://blog.csdn.net/bianxiezhe?spm=1000.2115.3001.5343",
					"color:red;font-weight: bold;");
			}
 
			//获取单击元素
			this.CElement = document.querySelector(this.options.ClickElement);
 
			//获取拖拽元素
			this.DElement = document.querySelector(this.options.DragElement);
 
			//获取返回按钮元素
			this.ReturnButtom = document.querySelector(this.options.ReturnButtom);
 
			//判断元素是否存在
			this.ReturnButtom = this.ReturnButtom ? this.ReturnButtom : false;
 
			//设置穿透，防止因子元素存在而不触发其事件的情况发生
			function SetPointerEvents(OPElement) {
				let Dom = document.querySelector(OPElement);
				let DomChid = {};
				(function DomChidFun(Dom, DomChid) {
					Object.keys(Dom.childNodes).forEach(function(key) {
						if (Dom.childNodes[key].nodeType === 1) {
							DomChid[Object.keys(DomChid).length] = Dom.childNodes[key];
							Dom.childNodes[key].style.cssText = 'pointer-events:none;';
							if (Dom.childNodes[key].length !== 0) {
								DomChidFun(Dom.childNodes[key], DomChid);
							}
						}
					});
				})(Dom, DomChid);
			}
 
			//是否需要单击事件
			if (this.options.OPPattern === 'click' || this.options.OPPattern === 'dragandclick') {
				// 判断元素是否存在
				if (this.options.DragElement) {
					//调用设置穿透函数
					SetPointerEvents(this.options.ClickElement);
				}
 
				//如果存在，则判断与与单击元素是否相同，如果不相同则绑定单击事件
				if (this.ReturnButtom !== false) {
					if (this.CElement.isEqualNode(this.ReturnButtom) === false) {
						this.ReturnButtom.addEventListener('click', function(event) {
							Whether = true;
							ClassThis.ReturnFun(ClassThis);
						});
					} else {
						console.warn('返回元素与单击元素相同，已屏蔽返回元素');
					}
				}
 
				//调用单击提交函数
				this.CElementFun(ClassThis);
			}
 
			//调用拖拽提交函数
			if (this.options.OPPattern === 'drag' || this.options.OPPattern === 'dragandclick') {
				// 判断元素是否存在
				if (this.options.DragElement) {
					//调用设置穿透函数
					SetPointerEvents(this.options.DragElement);
				}
				this.DElementFun(ClassThis);
			}
 
			//输出警告函数
			this.init.OutputWarning = function(ClassThis, OutputWarning) {
				if (ClassThis.options.OutputWarning === true) {
					console.warn(OutputWarning);
				}
			}
		}
 
		CElementFun(ClassThis) {
			let focus = null; //用于判断文件框状态（配合页面获焦）
			//单击元素被单击时触发
			ClassThis.CElement.addEventListener('click', (event) => {
				//阻止冒泡
				event.stopPropagation();
				//返回状态码
				ClassThis.options.OPState(2);
				//页面获焦时触发
				window.onfocus = function() {
					let FocusTimeout = null;
					//如果有选择了文件，则手动删除setInterval
					//同时使得window.onfocus=null，防止后面误触发
					const FocusInterval = setInterval(function() {
						if (focus !== null) {
							clearInterval(FocusInterval);
							clearTimeout(FocusTimeout);
							focus = null;
							window.onfocus = null;
						}
					}, 5);
					//如果setInterval在500毫秒后还没停止，则手动删除
					//同时使得window.onfocus=null，防止后面误触发
					FocusTimeout = setTimeout(function() {
						clearInterval(FocusInterval);
						ClassThis.options.OPState(7);
						window.onfocus = null;
					}, 150);
				}
			});
 
			//如果元素不是文件提交按钮，则另外创建文件提交按钮，事后立即删除按钮
			if (ClassThis.CElement.type !== 'file') {
				let input = document.createElement('input');
				input.type = 'file';
				input.multiple = 'multiple';
				document.querySelector('body').appendChild(input);
				ClassThis.CElement.addEventListener('click', (event) => {
					//阻止冒泡
					event.stopPropagation();
					setTimeout(function() {
						input.click();
					}, 5);
				});
				input.addEventListener('change', ChangePro);
				document.querySelector('body').removeChild(input);
			} else {
				ClassThis.CElement.addEventListener('change', ChangePro);
			}
 
			//按钮的值发生改变时触发
			function ChangePro(event) {
				//阻止默认事件
				event.preventDefault();
				//阻止冒泡
				event.stopPropagation();
				//返回状态码
				ClassThis.options.OPState(6);
				//获取文件框的结果
				let files = this.files;
				if (!files) return;
				//调用文件(文件夹)处理函数
				ClassThis.FileHandleFun(event, files, ClassThis);
				focus = this.value;
				//重置文件框的结果（清空）
				this.value = null;
			}
		}
 
		DElementFun(ClassThis) {
			// 在元素正在拖动到放置目标时触发,//不能注释，否则文件无论如何都会打开
			ClassThis.DElement.addEventListener('dragover', function(event) {
				//阻止默认事件
				event.preventDefault();
				//阻止冒泡
				event.stopPropagation();
				//返回状态码
				ClassThis.options.OPState(1);
			});
			// 在拖动的元素进入到放置目标时执行
			ClassThis.DElement.addEventListener('dragenter', function(event) {
				//阻止默认事件
				event.preventDefault();
				//阻止冒泡
				event.stopPropagation();
				//返回状态码
				ClassThis.options.OPState(3);
			});
			// 在可拖动的元素移出放置目标时执行
			ClassThis.DElement.addEventListener('dragleave', function(event) {
				//阻止默认事件
				event.preventDefault();
				//阻止冒泡
				event.stopPropagation();
				//返回状态码
				ClassThis.options.OPState(4);
			});
			// 在可拖动元素（文件）放置在目标元素中时执行
			ClassThis.DElement.addEventListener('drop', function(event) {
				//阻止默认事件
				event.preventDefault();
				//阻止冒泡
				event.stopPropagation();
				//返回状态码
				ClassThis.options.OPState(5);
				// 调用文件(文件夹)处理函数,为了判断是否为文件夹，所以放到后面处理了，这里用null代替files（即为空值）
				ClassThis.FileHandleFun(event, null, ClassThis);
			});
		}
 
		//文件(文件夹)处理函数
		async FileHandleFun(event, files, ClassThis) {
			//读取文件夹时，如果有多层文件夹就会同时文件数量超出限制时
			//会造成多次返回状态码，因此用One配合解决
			let One = true;
 
			//如果点击过“提交”一类按钮，重置结果集
			if (Whether === true) {
				Object.keys(getResult).forEach(function(key) {
					getResult['ResultType'] = null;
					getResult['UploadList'] = new Object();
					getResult['AttributeList'] = new Object();
				});
 
			}
 
			if (event.type === 'drop') {
				files = event.dataTransfer;
				let filesCount = 0;
				// 遍历所提交的文件夹
				for (var item of files.items) {
					// 获取到的每一个文件夹自带的方法，用于获取对应的内容
					item = item.webkitGetAsEntry();
					if (item.isDirectory) { //判断是否为文件夹
						//如果为文件夹，且为火狐浏览器
						if (navigator.userAgent.indexOf("Firefox") > 0) {
							console.warn('火狐浏览器暂时不支持提交文件夹');
							//返回状态码
							ClassThis.options.OPState(9);
							return null;
						}
						//获取文件并调用文件函数
						FolderFun(files, item);
					} else if (item.isFile) { //判断是否为文件
						//遍历到最后一次才调用
						if (filesCount === files.items.length - 1) {
							//获取文件并调用文件函数
							FileFun(files.files, ClassThis);
						}
						filesCount++;
					}
				}
			} else {
				//调用文件函数
				FileFun(files, ClassThis);
			}
 
			//文件函数
			function FileFun(files, ClassThis) {
				//返回状态码
				ClassThis.options.OPState(8);
				for (var item of files) {
					//判断格式是否为空
					if (item.type.replace(/(\s)/g, "")) {
						// 判断格式是否符合要求
						if (ClassThis.options.FormatList.includes(item.type.split('/')[1].toLowerCase())) {
							//判断数量限制是否有填或者文件数量是否小于限制数量
							if (ClassThis.options.QuantityLimit === null || (Object.keys(getResult[
										'UploadList']).length +
									Object.keys(TemporaryList).length) < ClassThis.options.QuantityLimit) {
								//将File对象添加进文件列表
								TemporaryList[Object.keys(TemporaryList).length] = {
									"FileName": item.name,
									"FileRoute": item.name,
									"File": item,
									"SubmitTime": new Date().getTime()
								};
								//返回状态码
								ClassThis.options.OPState(11);
							} else {
								//调用输出警告函数
								ClassThis.init.OutputWarning(ClassThis,
									`文件限制上传${ClassThis.options.QuantityLimit}个，已超出限制`);
								//返回状态码
								ClassThis.options.OPState(10);
								return null;
							}
						} else {
							//调用输出警告函数
							ClassThis.init.OutputWarning(ClassThis,
								`文件：${item.name.split('.')[0]}的格式为${item.type.split('/')[1].toLowerCase()}，不符合要求`
							);
						}
					} else {
						//调用输出警告函数
						ClassThis.init.OutputWarning(ClassThis, `文件：${item.name.split('.')[0]}的格式为空，不符合要求`);
					}
				}
				if (Object.keys(TemporaryList).length !== 0) {
					//调用ToolFun函数
					ClassThis.ToolFun(ClassThis, null);
				}
			}
 
			//文件夹函数async
			async function FolderFun(files, item) {
				if (item.isDirectory) { //如果是文件夹
					//读取文件夹
					let directoryReader = item.createReader();
					//读取和生成文件
					directoryReader.readEntries(function(entries) {
						//遍历文件夹
						entries.forEach(function(entry) {
							// 再次调用FolderFun函数
							FolderFun(files, entry);
						});
					});
				} else if (item.isFile) { //如果是文件
					//返回状态码
					ClassThis.options.OPState(8);
					//调用提取函数将文件取出（将FileEntry转为File）
					let resFile = await ExtractFile(item);
					//判断格式是否为空
					if (resFile.type.replace(/(\s)/g, "")) {
						// 判断格式是否符合要求
						if (ClassThis.options.FormatList.includes(resFile.type.split('/')[1]
								.toLowerCase())) {
							//判断数量限制是否有填或者文件数量是否小于限制数量
							if (ClassThis.options.QuantityLimit === null || ((Object.keys(getResult[
											'UploadList'])
										.length + Object.keys(TemporaryList).length) < ClassThis.options
									.QuantityLimit)) {
								//将File对象添加进文件列表或者文件数量是否小于限制数量
								TemporaryList[Object.keys(TemporaryList).length] = {
									"FileName": item.name,
									"FileRoute": item.fullPath,
									"File": resFile,
									"SubmitTime": new Date().getTime()
								};
								//返回状态码
								ClassThis.options.OPState(11);
								//文件数量等于限制数量则调用ToolFun函数
								if ((Object.keys(getResult['UploadList']).length + Object.keys(
										TemporaryList).length) ===
									ClassThis.options.QuantityLimit) {
									//防止在调用函数之后再次被调用
									clearTimeout(ClearSetTimeout);
									ClassThis.ToolFun(ClassThis, null);
									return null;
								} else {
									//在下一个File对象创建完成时删除定时器
									//与定时器配合，保障不会过多或不正常的调用ToolFun函数
									clearTimeout(ClearSetTimeout);
									ClearSetTimeout = setTimeout(function() {
										if (Object.keys(TemporaryList).length !== 0) {
											//调用ToolFun函数
											ClassThis.ToolFun(ClassThis, null);
										}
									}, 500);
									return null;
								}
							} else {
								if (One === true) {
									//调用输出警告函数
									ClassThis.init.OutputWarning(ClassThis,
										`文件限制上传${ClassThis.options.QuantityLimit}个，已超出限制`);
									//返回状态码
									ClassThis.options.OPState(10);
									One = false;
									return null;
								}
							}
						} else {
							//调用输出警告函数
							ClassThis.init.OutputWarning(ClassThis,
								`文件：${resFile.name.split('.')[0]}的格式为${resFile.type.split('/')[1].toLowerCase()}，不符合要求`
							);
						}
					} else {
						//调用输出警告函数
						ClassThis.init.OutputWarning(ClassThis,
							`文件：${resFile.name.split('.')[0]}的格式为空，不符合要求`);
					}
					//await：返回 Promise 对象的处理结果。如果等待的不是 Promise 对象，则返回该值本身
					//await：操作符用于等待一个Promise 对象。它只能在异步函数 async function 中使用。
				}
				//提取函数
				function ExtractFile(item) {
					//创建Promise对象后进一步创建File对象
					return new Promise((resolve, reject) => {
						item.file(res => {
							resolve(res);
						});
					});
				}
			}
		}
 
 
		ToolFun(ClassThis, DeleteState) {
			//说明返回类型
			getResult['ResultType'] = '部分结果';
 
			//清理返回列表
			Object.keys(getResult['AttributeList']).forEach(function(key) {
				delete getResult['AttributeList'][key];
			});
 
			Object.keys(TemporaryList).forEach(function(key) {
				//火狐没有lastModifiedDate,所以需要赋值
				if (!TemporaryList[key]['File'].lastModifiedDate) {
					TemporaryList[key]['File'].lastModifiedDate = new Date(TemporaryList[key]['File']
						.lastModified);
				}
 
				//将文件列表赋值给上传列表
				getResult['UploadList'][Object.keys(getResult['UploadList']).length] = TemporaryList[
					key];
 
				let PreviewFileO = '不支持该文件类型预览',
					PreviewFileT = '不支持该文件类型预览';
 
				//判断是否支持该文件类型预览
				if (TemporaryList[key]['File'].type.split('/')[0].toLowerCase() === 'image' ||
					TemporaryList[key][
						'File'
					].type.split('/')[0]
					.toLowerCase() === 'video' || TemporaryList[key]['File'].type.split('/')[0]
					.toLowerCase() === 'audio') {
					//判断是否支持该文件大小FileReader方式预览
					if ((Math.round(TemporaryList[key]['File'].size * 100 / 1024) / 100) <= 373760) {
						//创建FileReader对象
						const reader = new FileReader();
						//将读取到的文件编码成DataURL
						reader.readAsDataURL(TemporaryList[key]['File']);
						//FileReader预览方式赋值给变量
						PreviewFileT = reader;
					} else {
						PreviewFileT = '该属性不支持大小超过365MB的文件预览';
					}
					//将window.URL.createObjectURL(TemporaryList[key]['File'])预览方式赋值给变量
					PreviewFileO = window.URL.createObjectURL(TemporaryList[key]['File']);
				}
 
				//提取文件具体信息赋值给返回列表
				getResult['AttributeList'][Object.keys(getResult['AttributeList']).length] = {
					//文件名
					FileName: TemporaryList[key]['File'].name,
					//文件格式
					FileFormat: TemporaryList[key]['File'].type.toLowerCase(),
					//文件大小
					FileSize: Math.round(TemporaryList[key]['File'].size * 100 / 1024) / 100 + 'KB',
					//格式化的修改时间
					TormatTime: TemporaryList[key]['File'].lastModifiedDate.toLocaleDateString() +
						' ' +
						TemporaryList[key]['File'].lastModifiedDate
						.toTimeString().split(' ')[0],
					lastModified: TemporaryList[key]['File'].lastModified,
					//时间戳
					TimeDate: TemporaryList[key]['SubmitTime'],
					//文件路径
					FileRoute: TemporaryList[key]['FileRoute'],
					//window.URL.createObjectURL(TemporaryList[key]['File'])方式预览文件
					PreviewFileWin: PreviewFileO,
					//FileReader方式预览文件
					PreviewFileFR: PreviewFileT
				}
				delete TemporaryList[key];
			});
 
			//判断是否有传入DeleteState
			if (DeleteState !== null && DeleteState !== undefined) {
				//判断是否有传入DeleteState['DeleteOPState']
				if (DeleteState['DeleteOPState'] !== null && DeleteState['DeleteOPState'] !== undefined) {
					//判断DeleteState['DeleteOPState']是否为函数
					if (typeof DeleteState['DeleteOPState'] === 'function') {
						//返回状态码
						DeleteState['DeleteOPState'](12);
					}
				}
				//判断是否有传入DeleteState['DeleteResultSet']
				if (DeleteState['DeleteResultSet'] !== null && DeleteState['DeleteResultSet'] !== undefined) {
					//判断DeleteState['DeleteResultSet']是否为函数
					if (typeof DeleteState['DeleteResultSet'] === 'function') {
						//返回信息
						DeleteState['DeleteResultSet'](getResult);
					}
				}
			} else {
				//判断“提交”一类按钮存不存在
				if (this.ReturnButtom === false) {
					//如果提交按钮不存在就调用
					ClassThis.ReturnFun(ClassThis);
				} else {
					//如果提交按钮存在，返回部分结果用于预览
					ClassThis.options.ResultSet(getResult);
				}
			}
		}
 
		//删除函数
		DeleteFun(data) {
			//判断是否有传入“提交”一类按钮元素
			if (this.ReturnButtom !== false) {
				const DataArray = ['FileName', 'FileFormat', 'FileRoute', 'SubmitTime', 'lastModified',
					'FileSize', 'DeleteOPState', 'DeleteResultSet'
				];
				Object.keys(data).forEach(function(key) {
					if (!DataArray.includes(key)) {
						throw new Error('不接受其规定以外的属性');
					}
				});
				//遍历传入的data对象
				Object.keys(data).forEach(function(key) {
					//如果data[key]不为空
					if (data[key] !== null && data[key] !== undefined) {
						//如果key同时不等于SubmitTime、DeleteOPState、DeleteResultSet
						if (key !== 'SubmitTime' && key !== 'DeleteOPState' && key !==
							'DeleteResultSet') {
							//如果data[key]类型不等于string
							if (typeof data[key] !== 'string') {
								throw new Error(`${key}属性值的类型必须为string`);
							}
						}
						//如果key等于SubmitTime
						if (key === 'SubmitTime') {
							//如果data[key]类型不等于string同时不等于number
							if (typeof data[key] !== 'number' && typeof data[key] !== 'string') {
								throw new Error(`${key}属性值的类型必须为number或string`);
							}
						}
					}
 
					//如果data[key]类型不等于function
					if (typeof data[key] !== 'function') {
						//如果data[key]存在
						if (data[key]) {
							//如果data[key]类型不等于number
							if (typeof data[key] !== 'number') {
								//如果data[key]存在
								if (data[key].replace(/(\s)/g, "")) {
									//如果data['lastModified']存在或data['FileSize']
									if (data['lastModified'] || data['FileSize']) {
										// 如果data['FileSize']不存在
										if (!data['FileSize']) {
											// 如果data['lastModified']不存在
											if (!data['lastModified']) {
												throw new Error(`lastModified、FileSize至少存在一个`);
											} else {
												// 如果data['lastModified']不存在
												if (!data['lastModified'].replace(/(\s)/g, "")) {
													throw new Error(`lastModified、FileSize至少存在一个`);
												}
											}
										}
										// 如果data['lastModified']不存在
										if (!data['lastModified']) {
											// 如果data['FileSize']不存在
											if (!data['FileSize']) {
												throw new Error(`lastModified、FileSize至少存在一个`);
											} else {
												// 如果data['FileSize']不存在
												if (!data['FileSize'].replace(/(\s)/g, "")) {
													throw new Error(`lastModified、FileSize至少存在一个`);
												}
											}
										}
									} else {
										throw new Error(`lastModified、FileSize至少存在一个`);
									}
								} else if (key !== 'lastModified' && key !== 'FileSize') {
									// 如果key同时不等于lastModified、FileSize
									throw new Error(`${key}属性值的不能为空`);
								}
							}
						} else if (key !== 'lastModified' && key !== 'FileSize') {
							// 如果key同时不等于lastModified、FileSize
							throw new Error(`${key}属性值的不能为空`);
						}
					}
				});
 
				//声明并调用匿名函数
				(function(ClassThis) {
					//如果data['FileName']、data['FileFormat']、data['FileRoute']、data['SubmitTime']同时存在
					if (data['FileName'] && data['FileFormat'] && data['FileRoute'] && data['SubmitTime']) {
						//如果data['SubmitTime']的类型等于string
						if (typeof data['SubmitTime'] === 'string') {
							//如果data['FileName']、data['FileFormat']、data['FileRoute']、data['SubmitTime']同时存在
							if (data['FileName'].replace(/(\s)/g, "") && data['FileFormat'].replace(/(\s)/g,
									"") && data['FileRoute'].replace(/(\s)/g, "") && data['SubmitTime']
								.replace(/(\s)/g, "")) {
								// string转number
								data['SubmitTime'] = parseInt(data['SubmitTime']);
							} else {
								throw new Error(`FileName、FileFormat、FileRoute、SubmitTime必须同时存在`);
							}
						} else {
							//如果data['FileName']、data['FileFormat']、data['FileRoute'](、data['SubmitTime'])同时存在
							if (!data['FileName'].replace(/(\s)/g, "") && !data['FileFormat'].replace(
									/(\s)/g, "") && !data['FileRoute'].replace(/(\s)/g, "")) {
								throw new Error(`FileName、FileFormat、FileRoute、SubmitTime必须同时存在`);
							}
						}
					} else {
						throw new Error(`FileName、FileFormat、FileRoute、SubmitTime必须同时存在`);
					}
 
					//遍历getResult['UploadList']对象
					Object.keys(getResult['UploadList']).forEach(function(key) {
						//判断文件属性是否与传入的必要属性相同
						if (getResult['UploadList'][key]['FileName'] === data['FileName'] &&
							getResult['UploadList'][key][
								'File'
							]['type'] === data['FileFormat'] && getResult['UploadList'][key][
								'FileRoute'
							] ===
							data['FileRoute'] && getResult['UploadList'][key]['SubmitTime'] ===
							data[
								'SubmitTime']) {
							//计算文件大小（赋值给getResult['UploadList']对象时还没有计算）
							let FileSize = Math.round(getResult['UploadList'][key]['File']['size'] *
								100 /
								1)    / 100 + 'KB';
							//判断判断文件属性与传入的二选一属性相同
							if (FileSize === data['FileSize'] || getResult['UploadList'][key][
									'File'
								][
									'lastModified'
								] === data['lastModified']) {
								//删除相关文件
								delete getResult['UploadList'][key];
							} else {
								//赋值给TemporaryList对象
								TemporaryList[Object.keys(TemporaryList).length] = getResult[
									'UploadList'][key];
							}
						} else {
							//赋值给TemporaryList对象
							TemporaryList[Object.keys(TemporaryList).length] = getResult[
								'UploadList'][key];
						}
					});
 
					//清空getResult['UploadList']对象
					Object.keys(getResult['UploadList']).forEach(function(key) {
						delete getResult['UploadList'][key];
					});
 
					//创建DeleteState对象，用于将data['DeleteOPState']和
					//data['DeleteResultSet']传入ToolFun函数
					let DeleteState = new Object();
 
					//判断data['DeleteOPState']是否存在
					if (data['DeleteOPState'] !== null && data['DeleteOPState'] !== undefined) {
						//判断data['DeleteOPState']是否为函数
						if (typeof data['DeleteOPState'] === 'function') {
							//将data['DeleteOPState']赋值给DeleteState['DeleteOPState']
							DeleteState['DeleteOPState'] = data['DeleteOPState'];
						}
					}
 
					//判断data['DeleteResultSet']是否存在
					if (data['DeleteResultSet'] !== null && data['DeleteResultSet'] !== undefined) {
						//判断data['DeleteResultSet']是否为函数
						if (typeof data['DeleteResultSet'] === 'function') {
							//将data['DeleteResultSet']赋值给DeleteState['DeleteResultSet']
							DeleteState['DeleteResultSet'] = data['DeleteResultSet'];
						}
					}
 
					//如果DeleteState对象长度为0，则DeleteState等于null
					if (Object.keys(DeleteState).length === 0) {
						DeleteState = null;
					}
 
					//调用ToolFun函数
					ClassThis.ToolFun(ClassThis, DeleteState);
				})(this);
			} else {
				console.warn('未传入“提交”一类按钮元素');
			}
		}
 
		//返回函数
		ReturnFun(ClassThis) {
			getResult['WholeSize'] = 0;
			//说明返回类型
			getResult['ResultType'] = '全部结果';
			getResult['WholeformData'] = new FormData();
			getResult['WholeformData'].forEach(function(key) {
				getResult['WholeformData'].delete(key);
			});
			Object.keys(getResult['UploadList']).forEach(function(key) {
				getResult['UploadList'][key]['formData'] = new FormData();
				getResult['UploadList'][key]['formData'].delete("File");
				getResult['UploadList'][key]['formData'].append("File", getResult['UploadList'][key][
					'File'
				]);
				getResult['WholeformData'].append("File", getResult['UploadList'][key]['File']);
				getResult['WholeSize'] += getResult['UploadList'][key]['File']['size'];
			});
			//返回信息
			ClassThis.options.ResultSet(getResult);
		}
	}
 
	const FileUpload = (DPObject) => {
		const DPObjectArray = ['ClickElement', 'DragElement', 'OPPattern', 'ReturnButtom', 'QuantityLimit',
			'ConsoleAutograph', 'FormatList', 'OutputWarning', 'OPState', 'ResultSet'
		];
 
		Object.keys(DPObject).forEach(function(key) {
			if (!DPObjectArray.includes(key)) {
				throw new Error('不接受其规定以外的属性');
			}
			if (key === 'OPState') {
				//判断DPObject.OPState的类型是否为函数
				if (typeof DPObject.OPState !== 'function') {
					//删除之后在拼合之后也会缺失
					// delete DPObject.OPState;
					DPObject.OPState = FileUploadDP['OPState'];
				}
			}
			if (key === 'ResultSet') {
				//判断DPObject.ResultSet的类型是否为函数
				if (typeof DPObject.ResultSet !== 'function') {
					//删除之后在拼合之后也会缺失
					// delete DPObject.OPState;
					DPObject.ResultSet = FileUploadDP['ResultSet'];
				}
			}
		});
 
		//实例化Class
		const FileUploadHandle = new FileUploadClass();
		FileUpload.DeleteFun = (data) => FileUploadHandle.DeleteFun(data);
 
		const {
			ClickElement,
			DragElement,
			OPPattern,
			Delete,
			ReturnButtom
		} = DPObject;
		const DPObjectItme = {
			ClickElement,
			DragElement,
			OPPattern,
			Delete,
			ReturnButtom
		};
		Object.keys(DPObjectItme).forEach(function(key) {
			if (DPObjectItme[key] !== null && DPObjectItme[key] !== undefined) {
				if (typeof DPObjectItme[key] !== 'string') {
					throw new Error(`${key}属性值的类型必须为string`);
				}
			}
		});
		//先判断文件格式列表是否符合规则，不符合则没必要进行下去
		if (DPObject.FormatList) {
			if (typeof DPObject.FormatList !== 'object') {
				if (DPObject.FormatList.replace(/(\s)/g, "")) {
					throw new Error('文件格式列表类型必须为数组');
				} else {
					throw new Error('文件格式列表不能为空');
				}
			} else {
				DPObject.FormatList.forEach(function(item, index, self) {
					if (typeof DPObject.FormatList[index] === 'string') {
						DPObject.FormatList[index] = item.replace(/(\s)/g, "").toLowerCase();
					} else {
						throw new Error('文件格式类型必须为string');
					}
				});
			}
		} else {
			throw new Error('文件格式列表不能为空');
		}
 
		if (DPObject.QuantityLimit) {
			if (typeof DPObject.QuantityLimit === 'number' || typeof DPObject.QuantityLimit === 'string') {
				if (parseInt(DPObject.QuantityLimit)) {
					DPObject.QuantityLimit = parseInt(DPObject.QuantityLimit);
				} else {
					throw new Error('属性为字符串类型且无法转为数字类型');
				}
			} else {
				throw new Error('类型不正确');
			}
		}
 
		if (DPObject.ReturnButtom) {
			if (!DPObject.ReturnButtom.replace(/(\s)/g, "")) {
				throw new Error('提交键元素传入错误');
			}
		} else if (DPObject.ReturnButtom !== null && DPObject.ReturnButtom !== undefined) {
			throw new Error('提交键元素传入错误');
		}
 
		if (DPObject.OPPattern) { //如果DPObject.OPPattern存在且不为空
			if (DPObject.OPPattern.replace(/(\s)/g, "")) {
				DPObject.OPPattern = DPObject.OPPattern.replace(/(\s)/g, "").toLowerCase();
				if (DPObject.OPPattern === 'drag' || DPObject.OPPattern === 'click' || DPObject.OPPattern ===
					'dragandclick') {
					if (DPObject.OPPattern === 'drag') {
						DPObject.DragElement = DPObject.DragElement.replace(/(\s)/g, "");
						if (DPObject.DragElement) {
							if (DPObject.DragElement.replace(/(\s)/g, "")) {
								const DragElement = document.querySelector(DPObject.DragElement);
								if (DragElement) {
									//调用Class中的init函数
									FileUploadHandle.init(DPObject);
								} else {
									throw new Error('接受拖拽结果的元素传入错误');
								}
							} else {
								throw new Error('接受拖拽结果的元素不能为空');
							}
						} else {
							throw new Error('接受拖拽结果的元素不能为空');
						}
						return null;
					}
					if (DPObject.OPPattern === 'click') {
						if (DPObject.ClickElement) {
							DPObject.ClickElement = DPObject.ClickElement.replace(/(\s)/g, "");
							if (DPObject.ClickElement.replace(/(\s)/g, "")) {
								const ClickElement = document.querySelector(DPObject.ClickElement);
								if (ClickElement) {
									//调用Class中的init函数
									FileUploadHandle.init(DPObject);
								} else {
									throw new Error('接受单击提交结果的元素传入错误');
								}
							} else {
								throw new Error('接受单击提交结果的元素不能为空');
							}
						} else {
							throw new Error('接受单击提交结果的元素不能为空');
						}
						return null;
					}
					if (DPObject.OPPattern === 'dragandclick') {
						if (DPObject.DragElement && DPObject.ClickElement) {
							DPObject.DragElement = DPObject.DragElement.replace(/(\s)/g, "");
							DPObject.ClickElement = DPObject.ClickElement.replace(/(\s)/g, "");
							if (DPObject.DragElement.replace(/(\s)/g, "") && DPObject.ClickElement.replace(
									/(\s)/g, "")) {
								const DragElement = document.querySelector(DPObject.DragElement);
								const ClickElement = document.querySelector(DPObject.ClickElement);
								if (DragElement && ClickElement) {
									//调用Class中的init函数
									FileUploadHandle.init(DPObject);
								} else {
									throw new Error('接受拖拽结果的元素传入错误或接受单击提交结果的元素传入错误');
								}
							} else {
								throw new Error('接受拖拽结果和接受单击提交结果的元素都不能为空');
							}
						} else {
							throw new Error('接受拖拽结果和接受单击提交结果的元素都不能为空');
						}
						return null;
					}
				} else {
					throw new Error('操作模式只能为：drag、click、dragandclick其中一种');
				}
			} else {
				throw new Error('操作模式不能为空');
			}
		} else {
			throw new Error('操作模式不能为空');
		}
	}
	FileUpload.Delete = (data) => {
		if (FileUpload.DeleteFun) {
			FileUpload.DeleteFun(data);
		} else {
			throw new Error('未调用FileUpload函数');
		}
	}
	event.FileUpload = FileUpload;
})(window);
```
## 默认参数
```js
let FileUploadDP = { //默认参数
		ClickElement: null, //单击元素
		DragElement: null, //拖拽元素
		OPPattern: null, //操作模式
		ReturnButtom: null, //返回元素
		QuantityLimit: 1024, //数量限制
		ConsoleAutograph: true, //是否开启版权提示
		OutputWarning: true, //是否输出警告
		FormatList: [, 'svg+xml', 'avif', 'bmp', 'gif', 'ico', 'cur','jpg',
         'jpeg', 'jfif', 'pjpeg','pjp','png','svg', 'tif', 'tiff', 'webp',
          'ogg', 'mp3', 'wav', 'flac', 'mpeg', 'mp4', 'webm', 'avi', 'ogv'],
		OPState: function(OPState) {
			console.log("OPState:" + OPState);
		}, //操作状态
		ResultSet: function(ResultSet) {
			console.log(ResultSet);
		} //操作结果
	};
```
## 调用示例
```js
FileUpload({
		DragElement: '.UploadDiv',
		ClickElement: '#UploadDiv',
		OPPattern: 'DragandClick',
		ReturnButtom: '.FileDiv>div>input[type="button"]',
		ConsoleAutograph: true,
		OutputWarning: false,
		FormatList: ['png','jpg','jpeg','gif],
		QuantityLimit: 500,
		OPState: function(OPState) {
            console.log("OPState:" + OPState);
        },
        ResultSet:function(ResultSet) {
            console.log(ResultSet);
        }
});
```
## 删除文件示例
```js
FileUpload.Delete({
		FileName:（获得的文件名）,
		FileFormat: （获得的文件格式）,
		FileRoute: （获得的文件路径）,
		SubmitTime: （返回的提取文件时的时间戳）,
		lastModified: （文件最后的格式化时间）,
		FileSize: （文件大小）,
		DeleteOPState: function(DeleteOPState) {
            console.log(DeleteOPState);
        },//返回删除操作状态码
        DeleteResultSet: function(DeleteResultSet) {
			console.log(DeleteResultSet);
		}//返回删除后的结果
});
```

## 说明
1. DragElement和ClickElement都是非必须的，但必须有一个存在；同时，他们受OPPattern影响
2. ReturnButtom也是非必须的；但如果它不存在，那么只能增加文件，不能删除文件
3. QuantityLimit、ConsoleAutograph和OutputWarning都是非必须的；须注意OutputWarning，它可能在控制台输出很多警告
4. FormatList非必须的；如果它存在，那么它必须是数组
5. OPState、ResultSet和DeleteOPState、DeleteResultSet都是非必须的；如果它存在，那么它必须是函数（方法）；否则将有可能被默认参数替代或报错，亦或无法得到操作状态和结果
6. 想要调用FileUpload.Delete函数（方法），必须先调用FileUpload函数（方法），否则会报错
7. FileName、FileFormat、FileRoute必须存在，且格式必须为String
8. SubmitTime必须存在，且格式必须为String或Number
9. lastModified和FileSize至少存在一个，且必须为函数（方法）
10. 返回的数据类型将是整数或对象
11. 被调用的两个方法都只接受规定的属性，不接受外来属性
12. 版本为v1.0.01，第一次试着写，欢迎指教


```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<!-- 禁止浏览器缓存，配合加版本号，双重防止 -->
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<title>上传文件</title>
		<link rel="stylesheet" href="./icomoon/style.css" />
		<link rel="stylesheet" href="./css/FileUpload.css?Version=v1.0.01" />
		<script src="./JavaScript/FileUpload.js?Version=v1.0.01"></script>
	</head>
	<body>
		<div class="PreviewList"></div>
		<div class="FileDiv">
			<div>
				<div class="UploadDiv" id="UploadDiv">
					<div>
						<i class="icon-file-music"></i>
						<i class="icon-file-picture"></i>
						<i class="icon-file-play"></i>
						<span>将文件拖拽到我这里上传</span>
					</div>
				</div>
				<input type="button" value="提交" />
				<input type="submit" value="上传" />
			</div>
		</div>
		<div class="tipsANDenlarge">
			<div class="TipsDiv">TipsDiv</div>
			<div class="Enlarge" id="Enlarge">
				<div id="left">
					<i class="icon-back"></i>
				</div>
				<div id="MainDiv"></div>
				<div id="right">
					<i class="icon-right"></i>
				</div>
			</div>
		</div>
		<script src="./JavaScript/jquery-3.6.0.js"></script>
		<script type="text/javascript">
			const FileDiv = document.querySelector('.FileDiv'),
				UploadDiv = document.querySelector('#UploadDiv'),
				Span = document.querySelector('span'),
				TipsDiv = document.querySelector('.TipsDiv'),
				Enlarge = document.querySelector('.Enlarge');
 
			FileUpload({
				DragElement: '.UploadDiv',
				ClickElement: '#UploadDiv',
				OPPattern: 'DragandClick',
				ReturnButtom: '.FileDiv>div>input[type="button"]',
				ConsoleAutograph: true,
				OutputWarning: false,
				FormatList: ['html', 'javascript', 'css', 'apng', 'svg+xml', 'avif', 'bmp', 'gif', 'ico', 'x-icon',
					'cur', 'jpg', 'jpeg', 'jfif',
					'pjpeg', 'pjp', 'png',
					'svg', 'tif', 'tiff', 'webp', 'ogg', 'mp3', 'wav', 'aac', 'flac', 'mpeg', 'mp4', 'webm', 'avi',
					'ogv'
				],
				QuantityLimit: 500,
				OPState: function(OPState) {
					if (OPState == 1 || OPState === 2 || OPState == 3) {
						UploadDiv.style.cssText = 'color:#fe7300;border-color:#fe7300;';
						Span.innerText = '请松开你的爪子';
					}
					if (OPState == 4 || OPState == 5 || OPState == 6 || OPState == 7) {
						Span.innerText = '将文件拖拽到我这里上传';
						UploadDiv.style.cssText = 'color:gray;border-color:gray;';
					}
					if (OPState === 10) {
						TipsDiv.innerText = "文件读取数量超出限制";
						TipsDiv.style.cssText = 'display:block;pointer-events: auto;';
						setTimeout(function() {
							TipsDiv.style.opacity = 1;
						}, 10);
						setTimeout(function() {
							TipsDiv.style.opacity = 0;
						}, 2010);
						setTimeout(function() {
							TipsDiv.style.cssText = 'display:none;pointer-events: auto;';
						}, 3510);
					}
				},
				ResultSet:1,
				ResultSet: function(ResultSet) {
					console.log(ResultSet);
					if (ResultSet['ResultType'] === "部分结果") {
						var PreviewList = document.querySelector('.PreviewList');
						Object.keys(ResultSet['AttributeList']).forEach(function(key) {
							if (ResultSet['AttributeList'][key]['PreviewFileWin']) {
								FileDiv.style.height = "calc(100% - 180px)";
								if (ResultSet['AttributeList'][key]['FileFormat'].split('/')[0] ===
									'image') {
									PreviewList.innerHTML += `<div class="PreviewDiv"  data-filename="${ResultSet['AttributeList'][key]['FileName']}" data-fileformat="${ResultSet['AttributeList'][key]['FileFormat']}" data-fileroute="${ResultSet['AttributeList'][key]['FileRoute']}" data-timedate="${ResultSet['AttributeList'][key]['TimeDate']}" data-lastmodified="${ResultSet['AttributeList'][key]['lastModified']}"  data-filesize="${ResultSet['AttributeList'][key]['FileSize']}">
									<img src="${ResultSet['AttributeList'][key]['PreviewFileWin']}"/>
									    <button>删除</button>
									</div>`;
								}
 
								if (ResultSet['AttributeList'][key]['FileFormat'].split('/')[0] ===
									'video') {
									PreviewList.innerHTML += `<div class="PreviewDiv"  data-filename="${ResultSet['AttributeList'][key]['FileName']}" data-fileformat="${ResultSet['AttributeList'][key]['FileFormat']}" data-fileroute="${ResultSet['AttributeList'][key]['FileRoute']}" data-timedate="${ResultSet['AttributeList'][key]['TimeDate']}" data-lastmodified="${ResultSet['AttributeList'][key]['lastModified']}"  data-filesize="${ResultSet['AttributeList'][key]['FileSize']}">
										<video src="${ResultSet['AttributeList'][key]['PreviewFileWin']}" preload controls></video>
										<button>删除</button>
										</div>`;
								}
								if (ResultSet['AttributeList'][key]['FileFormat'].split('/')[0] ===
									'audio') {
									PreviewList.innerHTML += `<div class="PreviewDiv"  data-filename="${ResultSet['AttributeList'][key]['FileName']}" data-fileformat="${ResultSet['AttributeList'][key]['FileFormat']}" data-fileroute="${ResultSet['AttributeList'][key]['FileRoute']}" data-timedate="${ResultSet['AttributeList'][key]['TimeDate']}" data-lastmodified="${ResultSet['AttributeList'][key]['lastModified']}"  data-filesize="${ResultSet['AttributeList'][key]['FileSize']}">
										<audio src="${ResultSet['AttributeList'][key]['PreviewFileWin']}" preload controls></audio>
										<button>删除</button>
										</div>`;
								}
							}
						});
 
					} else {
						document.querySelector('.FileDiv>div>input[type="submit"]').addEventListener('click',
							function() {
								Object.keys(ResultSet['UploadList']).forEach(function(key) {
									var formData = new FormData();
									formData.append('File', ResultSet['UploadList'][key]['File'],
										ResultSet['UploadList'][key]['FileRoute'].replace(/(\/)/g,
											"*"));
									$.ajax({
								 	url: './FileUpload.php',
										type: 'POST',
										cache: false,
										data: formData,
										processData: false,
										contentType: false,
										success: function(res) {
											console.log(res);
										},
										error: function(res) {
											console.log(res);
										}
									});
								});
							});
					}
				}
			});
 
			document.querySelector(".PreviewList").addEventListener("dblclick", function(event) {
				let Target = event.target.parentNode;
				const ThisChil = [...this.childNodes];
				if (event.target.localName !== "img") return;
				document.querySelector("#MainDiv").innerHTML = Target.innerHTML.replace(/(<button>删除<\/button>)/g, "");
				Enlarge.style.display = "flex";
				setTimeout(function() {
					Enlarge.style.opacity = 1;
				}, 100);
 
				document.querySelector("#left").addEventListener("click", LeftFun);
 
				function LeftFun(event) {
					if ((ThisChil.indexOf(Target) - 1) >= 0) {
						let Chil = ThisChil[ThisChil.indexOf(Target) - 1].firstChild.nextElementSibling;
						if (Chil.localName === "img") {
							document.querySelector("#MainDiv").innerHTML = Chil.parentNode.innerHTML.replace(
								/(<button>删除<\/button>)/g, "");
							Target = ThisChil[ThisChil.indexOf(Target) - 1];
						} else {
							Target = ThisChil[ThisChil.indexOf(Target) - 1];
							LeftFun(event);
						}
					} else {
						TipsDiv.innerText = "已经是第一张了";
						TipsDiv.style.display = 'block';
						setTimeout(function() {
							TipsDiv.style.opacity = 1;
						}, 10);
						setTimeout(function() {
							TipsDiv.style.opacity = 0;
						}, 2010);
						setTimeout(function() {
							TipsDiv.style.display = 'none';
						}, 3510);
					}
				}
 
				document.querySelector("#right").addEventListener("click", RightFun);
 
				function RightFun(event) {
					if ((ThisChil.indexOf(Target) + 1) < ThisChil.length) {
						let Chil = ThisChil[ThisChil.indexOf(Target) + 1].firstChild.nextElementSibling;
						if (Chil.localName === "img") {
							document.querySelector("#MainDiv").innerHTML = Chil.parentNode.innerHTML.replace(
								/(<button>删除<\/button>)/g, "");
							Target = ThisChil[ThisChil.indexOf(Target) + 1];
						} else {
							Target = ThisChil[ThisChil.indexOf(Target) + 1];
							RightFun(event);
						}
					} else {
						TipsDiv.innerText = "已经是最后一张了";
						TipsDiv.style.display = 'block';
						setTimeout(function() {
							TipsDiv.style.opacity = 1;
						}, 10);
						setTimeout(function() {
							TipsDiv.style.opacity = 0;
						}, 2010);
						setTimeout(function() {
							TipsDiv.style.display = 'none';
						}, 3510);
					}
				}
 
				document.querySelector(".Enlarge").addEventListener("click", EnlargeFun);
 
				function EnlargeFun(event) {
					if (event.target === this) {
						Enlarge.style.cssText = "display:flex;opacity:0;";
						setTimeout(function() {
							Enlarge.style.display = "none";
							document.querySelector("#MainDiv").innerHTML = "";
							document.querySelector("#left").removeEventListener("click", LeftFun);
							document.querySelector("#right").removeEventListener("click", RightFun);
							document.querySelector(".Enlarge").removeEventListener("click", EnlargeFun);
						}, 500);
					}
				}
			});
 
			document.querySelector('.PreviewList').addEventListener('click', function(event) {
				if (event.target.localName === 'button') {
					FileUpload.Delete({
						FileName: event.target.parentNode.getAttribute('data-filename'),
						FileFormat: event.target.parentNode.getAttribute('data-fileformat'),
						FileRoute: event.target.parentNode.getAttribute('data-fileroute'),
						SubmitTime: event.target.parentNode.getAttribute('data-timedate'),
						lastModified: event.target.parentNode.getAttribute('data-lastmodified'),
						FileSize: event.target.parentNode.getAttribute('data-filesize'),
						DeleteOPState: function(DeleteOPState) {
							if (DeleteOPState === 12) {
								event.target.parentNode.parentNode.removeChild(event.target.parentNode);
								TipsDiv.innerText = "删除成功";
								TipsDiv.style.display = 'block';
								setTimeout(function() {
									TipsDiv.style.opacity = 1;
								}, 10);
								setTimeout(function() {
									TipsDiv.style.opacity = 0;
								}, 2010);
								setTimeout(function() {
									TipsDiv.style.display = 'none';
								}, 3510);
							}
						},
						DeleteResultSet: function(DeleteResultSet) {
							if (Object.keys(DeleteResultSet['AttributeList']).length === 0) {
								FileDiv.style.height = "100%";
							}
						}
					});
				}
			});
		</script>
	</body>
</html>
```