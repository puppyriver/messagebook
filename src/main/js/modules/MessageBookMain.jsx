import React from 'react';
// import '../lib/wing.min.css'
import {MessageList} from '../components'
import Ajax from '../common/ajax'



var PropTypes = React.PropTypes;


export default class MessageBookMain extends React.Component {
    static defaultProps = {
      //  messages : null
    };

    state = {
        messages : [] ,
        editMessage : {

        }
    };

    componentWillMount() {
        Ajax.post("ajax/message/all?page=234",{page : '111'},result=>{
            this.setState({messages : result})
        })
        // $.post("ajax/sys/queryUserRoles",{},
        //     (result) =>{
        //
        //         this.setState({
        //             roleOptions : result
        //         });
        //     });
    };
    componentWillReceiveProps(nextProps) {

        // if (nextProps.stpKey) {
        //     this.fetch(nextProps.stpKey);
        // }
    }

    save = ()=> {
        if (this.state.editMessage.content) {
            Ajax.post("ajax/message/save",{page : '111',message : JSON.stringify(this.state.editMessage)},result=>{
                this.setState({messages : [result,...this.state.messages.filter(msg=>msg.id != result.id)],editMessage : {}})
            })
        }
    }

    delete = (message)=> {
        if (message) {
            Ajax.post("ajax/message/delete",{messageId : message.id},result=>{
                this.setState({messages : [...this.state.messages.filter(msg=>msg.id != message.id)]});
            })
        }
    }


    render = ()=> {
        // let msgBtnStyle={float : 'right'};
        let editMessage = this.state.editMessage;
        return (<div>
            <div className="nav">
                <h1 className="nav-logo">Message Book</h1>
                <a className="nav-item" href="index.html">笔记管理</a>
                <a className="nav-item" href="files.html">文件管理</a>
            </div>
            <div style={{marginTop : 10}}   className="row">
                <div className="col-1">
                   
                </div>
                <div className="col-10">
                    <textarea
                        type="text" placeholder=".."
                        value={editMessage.content ? editMessage.content : ""}
                        onChange={e=>{
                            this.setState({
                                editMessage : {...editMessage,content : e.target.value}
                            });
                        }}
                        // style={{height : "100%"}}
                        
                        rows={((!editMessage.content || editMessage.content.indexOf('\n')==-1)?10:(editMessage.content.match(/\n/gi)).length+1)}
                    >
                    </textarea>

                </div>
                <div className="col-1">
                    <button  onClick={this.save}>保存</button>
                </div>
            </div>
           <MessageList messages={this.state.messages} editor={(message=>{
               this.setState({editMessage : message});
           })} deleter = {this.delete}/>
        </div>);
    }
}

MessageBookMain.propTypes = {
}
