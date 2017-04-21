import React from 'react';
import '../lib/wing.min.css'


var PropTypes = React.PropTypes;


export default class MessageList extends React.Component {
    static defaultProps = {
        messages : null,
        editor : ()=>{},
        deleter : (msg)=>{}
    };

    state = {
        visible: false
    };

    componentWillMount() {
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

    render = ()=> {
        let messages = this.props.messages;
        let msgBtnStyle={float : 'right'};
        return (<div>
            {
                messages && messages.map(m =>
                    <div  style={{marginTop : 0}}  className="row">
                        <div className="col-1">
                            <div style={{marginTop : 10}}>{m.time && new Date(m.time).toLocaleString()}</div>
                        </div>
                        <div className="col-10">
                            <pre style={{margin : 2}}>

                                <code  style={{margin : 0}}>
                                    {m.content}
                                </code>
                            </pre>
                        </div>
                        <div className="col-1">
                             <div style={{marginTop : 10}}>
                                <button style={{margin : 0,padding : 2}}  onClick={()=>this.props.editor(m)}>编辑</button>
                                <button  style={{margin : 0,marginLeft : 5,padding : 2}}  onClick={()=>this.props.deleter(m)}>删除</button>
                             </div>
                        </div>
                    </div>                                  
                )
            }
        </div>);
    }





}

MessageList.propTypes = {
    messages: React.PropTypes.array,
    editor: PropTypes.func,
    deleter : PropTypes.func
}
