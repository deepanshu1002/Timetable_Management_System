import React from 'react';
import { useState, useEffect } from 'react'
import '../css_file/forgotPassword.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { createUrl , log} from '../utils/utils';

import { toast } from 'react-toastify'
import axios from 'axios';
import { async } from '../services/department';





function ForgotPassword() {
  
    const [email, setEmail] = useState("")

    const SendEmail=  async ()=>{
        const url = createUrl('/forgotpassword')
        const body = {
          email
        }
        try {
          const response = await axios.post(url, body)
          log(response.data)
          return response.data
        }catch(ex){
          log(ex)
          return null
        }
      }
	return (
		<div>
			<div class="container padding-bottom-3x mb-2">
    <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10">
            <h2>Forgot your password?</h2>
            <p>Change your password in three easy steps. This helps to keep your new password secure.</p>
            <ol class="list-unstyled">
                <li><span class="text-primary text-medium">1. </span>Fill in your email address below.</li>
                <li><span class="text-primary text-medium">2. </span>We'll email you a temporary code.</li>
                <li><span class="text-primary text-medium">3. </span>Use the code to change your password on our secure website.</li>
            </ol>
            <form class="card mt-4">
                <div class="card-body">
                    <div class="form-group">
                        <label for="email-for-pass">Enter your email address</label>
                        <input class="form-control" type="email" id="email-for-pass"  onChange={(e)=>setEmail(e.target.value)} required=""/>
                        <small class="form-text text-muted" >Type in the email address you used when you registered. Then we'll email a code to this address.</small>
                    </div>
                </div>
                <div class="card-footer">
                    <button class="btn btn-primary" type="submit" onClick={()=>SendEmail()}>Get New Password</button>
                </div>
            </form>
        </div>
    </div>
</div>
		</div>
	);
}
export default ForgotPassword;