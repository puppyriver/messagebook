import React from 'react';
// import '../lib/wing.min.css'
import {MessageList} from '../components'
import Ajax from '../common/ajax'
import {FileInput} from '../components'



var PropTypes = React.PropTypes;


export default class FileManagerMain extends React.Component {
    static defaultProps = {
        //  messages : null
    };

    state = {
        fileInfo : null,
        path : "",
        upload : {
            progress : 0 ,
            label : ''
        }
    };

    componentWillMount() {
        this.list();
    };
    componentWillReceiveProps(nextProps) {

        // if (nextProps.stpKey) {
        //     this.fetch(nextProps.stpKey);
        // }
    }

    save = ()=> {

    }

    delete = (file)=> {

    }

    list = (path=this.state.path)=> {

        Ajax.post("ajax/file/list",{path : path},result=>{
            this.setState({path : result.absolutePath,fileInfo : result})
        })
    }





    render = ()=> {
        let upload = this.state.upload || {};
        let progress = upload.progress || 0;
        let allowedFileTypes = ["image/png", "image/jpeg", "image/gif"];
        let fileIsIncorrectFiletype =(file)=>{
            console.log(`file.type = ${file.type}`);
            if (allowedFileTypes.indexOf(file.type) === -1) {
                return false;
            } else {
                return false;
            }
        }

        let handleFileSelected =(event, file)=>{

            let form = document.getElementById("uploadForm");
            var data = new FormData(form);
            data.append("file",file);
            var idx = -1;
            var xhr = new XMLHttpRequest();
            xhr.open('POST', 'ajax/file/upload');
            //     xhr.responseType = 'json';

            xhr.upload.onload = ()=>{
                console.log('=====finish uploading')

            }
            xhr.upload.onloadstart =  (evt1,evt2)=>{
                console.log(evt1);
                console.log(evt2);
                idx ++;
            }
            xhr.upload.onprogress =   (ev)=>{
                console.log('======onprogress')
                if(ev.lengthComputable) {
                    var percent = parseInt(100 * ev.loaded/ev.total);

                    console.log(percent,ev)
                    this.setState({
                        upload: {...this.state.upload,progress : percent,label : `${parseInt(ev.loaded/1000)}/${parseInt(ev.total/1000)} kb`}
                        //    now : parseInt(percent),
                        //   label : '正在上传文件:'+this.state.files[idx].name+" "+parseInt(ev.loaded / 1024)+"kb/"+parseInt(ev.total/1024)+"kb"
                    });
                }
            }

            xhr.onload = ()=> {
                console.log(`xhr onload success !`);
                this.list();
                //success(xhr.response);
            };
            xhr.send(data);

//            this.setState({file: file, fileContents: event.target.result});
        }



        let tableStyle = {
            //display: "block",
            width : "80%",
        paddingLeft : "10em",
        "overflow-x": "auto",
        "-webkit-overflow-scrolling": "touch"};
        let fileInfo = this.state.fileInfo;
        // let msgBtnStyle={float : 'right'};
        let editMessage = this.state.editMessage;
        return (<div>
            <div className="nav">
                <h1 className="nav-logo">Message Book</h1>
                <a className="nav-item" href="index.html">笔记管理</a>
                <a className="nav-item" href="files.html">文件管理</a>
            </div>
            <div style={{marginTop : 10}}>
                <div className="col-12">
                    <span>


                        <span  style={{fontSize : 15}}>

                              <label >

                                <FileInput
                                    readAs='binary'
                                    // readAs= 'dataUrl'
                                    style={ { display: 'none' } }

                                    // onLoadStart={this.showProgressBar}
                                    onLoad={handleFileSelected}
                                    // onProgress={updateProgressBar}

                                    cancelIf={fileIsIncorrectFiletype}
                                    // abortIf={this.cancelButtonClicked}

                                    // onCancel={this.showInvalidFileTypeMessage}
                                    // onAbort={this.resetCancelButtonClicked}
                                />
                               <span  style={{fontSize : 15}}>
                                   <a>{`[${fileInfo && fileInfo.absolutePath}]     `}</a>
                                    <a>上传文件</a>
                               </span>
                            </label>
                            <a style={{marginLeft : 10}} onClick={()=>this.setState({dialog : "open"})}>新建文件夹</a>
                            <a style={{marginLeft : 10}} onClick={()=>this.list()}>刷新</a>
                        </span>
                    </span>

                    
                    <div style={{width : "80%",marginBottom : 10,marginTop : 10}} className ="progress">
                        <span className={progress == 100 ? "green": "orange"} style={{width: progress+"%"}}><span>{upload.label+"   "+progress+"%"}</span></span>
                    </div>

                    <dialog open = { this.state.dialog && "open"}>
                        <div style={{paddingRight : 20}}>
                            <input type="text" value={this.state.editFolderName} onChange={e=>{
                                this.setState({editFolderName  : e.target.value})
                            }}/>
                            <button
                                onClick={()=>{
                                    Ajax.post("ajax/file/mkdir",{path : this.state.path,folderName : this.state.editFolderName},result=>{
                                        this.setState({dialog : null});
                                        this.list();
                                    })
                                }}
                            >确定</button>
                            <button
                                onClick={()=>{
                                    this.setState({dialog : null});
                                }}
                            >取消</button>
                        </div>
                    </dialog>

                    <table style={tableStyle} border="1">
                        <thead>
                            <th>Name</th>
                            <th>#</th>
                            <th>#</th>
                            <th>Size</th>
                            <th>Time</th>

                        </thead>
                        <tbody>
                        {
                            fileInfo && fileInfo.subFiles && fileInfo.subFiles
                                .map(info=><tr>
                                    <td>{info.directory ?
                                        <a onClick={()=>{
                                            this.list(info.absolutePath);
                                        }}>{`[${info.name}]`}</a> : <a  href={"ajax/file/download?path="+info.absolutePath}>
                                            <b>{info.name}</b></a>}
                                    </td>
                                    <td>{info.directory ?
                                        "": <a href={"ajax/file/download?path="+info.absolutePath}>下载</a>}
                                    </td>
                                    <td>{info.name == '..' ?
                                        "": <a onClick={()=>{
                                                Ajax.post("ajax/file/delete?path="+info.absolutePath,{},()=>{this.list()})
                                            }}
                                         >
                                         删除
                                         </a>}
                                    </td>
                                    <td>{info.size}</td>
                                    <td>{new Date(info.time).toLocaleString()}</td>

                                </tr>)
                        }
                        </tbody>
                    </table>

                </div>
            </div>
        </div>);
    }
}

