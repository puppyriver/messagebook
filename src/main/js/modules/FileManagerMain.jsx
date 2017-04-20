import React from 'react';
// import '../lib/wing.min.css'
import {MessageList} from '../components'
import Ajax from '../common/ajax'



var PropTypes = React.PropTypes;


export default class FileManagerMain extends React.Component {
    static defaultProps = {
        //  messages : null
    };

    state = {
        fileInfo : null
    };

    componentWillMount() {
        Ajax.post("ajax/file/list",{path : '.'},result=>{
            this.setState({fileInfo : result})
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

    }

    delete = (file)=> {

    }


    render = ()=> {
        let fileInfo = this.state.fileInfo;
        // let msgBtnStyle={float : 'right'};
        let editMessage = this.state.editMessage;
        return (<div>
            <div className="nav">
                <h1 className="nav-logo">Message Book</h1>
                <a className="nav-item" href="#">Item</a>
                <a className="nav-item" href="#">Item</a>
                <a className="nav-item" href="#">Item</a>
            </div>
            <div style={{marginTop : 10}}   className="row">
                <div className="col-12">
                    <table className="full-screen" border="1">
                        <thead>
                            <td>Name</td>
                            <td>Size</td>
                            <td>Time</td>
                        </thead>
                        <tbody>
                        {
                            fileInfo && fileInfo.subFiles
                                .map(info=><tr>
                                    <td>{info.name}</td>
                                    <td>{info.size}</td>
                                    <td>{info.time}</td>
                                </tr>)
                        }
                        </tbody>
                    </table>
                </div>
            </div>
        </div>);
    }
}

