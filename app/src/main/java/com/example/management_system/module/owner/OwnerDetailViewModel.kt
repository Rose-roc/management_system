package com.example.management_system.module.owner

import android.text.TextUtils
import com.example.management_system.data.Family
import com.example.management_system.data.House
import com.example.management_system.data.Owner
import com.example.management_system.utils.ExceptionUtil
import com.zhan.mvvm.mvvm.BaseViewModel
import com.zhan.mvvm.mvvm.livedata.CommonLiveData

class OwnerDetailViewModel : BaseViewModel<OwnerRepository>() {
    var houseData = CommonLiveData<House>()
    var familyData = CommonLiveData<Family>()
    var ownerDeleteData = CommonLiveData<String>()
    var ownerUpdateData = CommonLiveData<String>()

    fun findHouseById(houseId: Int) {
        quickLaunch<House> {
            onStart { showLoading() }
            request { repository.findHouseById(houseId) }
            onSuccess {
                hideLoading()
                houseData.value = it
            }
            onFailure {
                hideLoading()
                houseData.failureMessage = it
            }
            onException {
                hideLoading()
                houseData.exception = ExceptionUtil.parseException2(it)
            }
        }
    }

    fun findFamilyById(familyId: Int) {
        quickLaunch<Family> {
            onStart { showLoading() }
            request { repository.findFamilyById(familyId) }
            onSuccess {
                hideLoading()
                familyData.value = it
            }
            onFailure {
                hideLoading()
                familyData.failureMessage = it
            }
            onException {
                hideLoading()
                familyData.exception = ExceptionUtil.parseException2(it)
            }
        }
    }

    fun deleteOwner(ownerId: Int) {
        quickLaunch<String> {
            onStart { showLoading() }
            request { repository.deleteOwner(ownerId) }
            onSuccess {
                hideLoading()
                ownerDeleteData.value = it
            }
            onFailure {
                hideLoading()
                ownerDeleteData.failureMessage = it
            }
            onException {
                hideLoading()
                ownerDeleteData.exception = ExceptionUtil.parseException2(it)
            }
        }
    }

    fun updateOwner(ownerId: Int, name: String, phone: String, houseId: Int, familyId: Int) {
        quickLaunch<String> {
            onStart { showLoading() }
            request {
                if (houseId == 0 && familyId == 0) repository.updateOwner(ownerId, name, phone)
                else repository.updateOwner(ownerId, name, phone, houseId, familyId)
            }
            onSuccess {
                hideLoading()
                ownerUpdateData.value = it
            }
            onFailure {
                hideLoading()
                ownerUpdateData.failureMessage = it
            }
            onException {
                hideLoading()
                ownerUpdateData.exception = ExceptionUtil.parseException2(it)
            }
        }
    }
}