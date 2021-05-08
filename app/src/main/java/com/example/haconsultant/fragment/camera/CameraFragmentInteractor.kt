package com.example.haconsultant.fragment.camera

interface CameraFragmentInteractor {
    fun onCameraBack()

    //Поменять
    fun onCameraOk(codeVendor: String? = null, name: String? = null, password: String? = null)
}