import { useDispatch, useSelector } from 'react-redux'
import { Route, Routes } from 'react-router-dom'
import './App.css'
import { useEffect } from 'react'
//used to register react-toastify
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
import{Admin,AddDetails} from './components/admin'
// import{AddDetails} from './components/admin'
import RegisterUser from './components/registerUser'
import ManageUsers from './components/ManageUser'
import LeaveApplication from './components/leaveApplication'
import ClassRoom from './components/classRoom'
import LoginUser from './components/loginUser';
import Student from './components/student';
import Teacher from './components/teacher';
import AddSubjectDetails from './components/subjectDetails';
import AddLabVenue from './components/labDetails';
import AddClassroom from './components/classRoomDetails';
import AddDepartment from './components/departmentDetails';

function App() {
return (
    <div className='container-fluid'>
    
      <div className='container'>
        <Routes>
        <Route path='/' element={<LoginUser/>} />
         {/* register component */}
        <Route path='/adddetails' element={<AddDetails/>} />
         <Route path='/register' element={<RegisterUser />} />
         <Route path='/validuser' element={<ManageUsers />} />  
         {/*Leave Application component */}
         <Route path='/LeaveApplication' element={<LeaveApplication />} />
         {/* Class Room Component */}
         <Route path='/ClassRoom' element={<ClassRoom />} />

         <Route path='/student' element={<Student/>} />

         <Route path='/teacher' element={<Teacher/>} />

         <Route path='/admin' element={<Admin/>} />
         
          <Route path='/managedepartment' element={<AddDepartment/>} />
          
         <Route path='/manageclassroom' element={<AddClassroom/>} />

         <Route path='/managelab' element={<AddLabVenue/>} />

         <Route path='/managesubject' element={<AddSubjectDetails/>} />

        </Routes>
      </div>
      <ToastContainer />
    </div>
  )
}
  export default App
