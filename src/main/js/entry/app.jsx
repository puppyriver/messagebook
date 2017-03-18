import ReactDOM from 'react-dom';
import React from 'react';

// import 'purecss/build/base-min.css'
// import 'purecss/build/pure-min.css'
// import 'purecss/build/grids-core-min.css.css'
// import 'purecss/build/tables-min.css'
// import '../lib/bijou.min.css'
import '../lib/wing.min.css'
import MessageBookMain from '../modules/MessageBookMain.jsx'

var div = document.getElementById('react-content');
var module = div.getAttribute("module");

if (module == 'main') ReactDOM.render(<MessageBookMain/>,div);

