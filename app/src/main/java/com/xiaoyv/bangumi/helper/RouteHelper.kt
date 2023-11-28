package com.xiaoyv.bangumi.helper

import androidx.core.os.bundleOf
import com.blankj.utilcode.util.ActivityUtils
import com.xiaoyv.bangumi.ui.HomeActivity
import com.xiaoyv.bangumi.ui.feature.calendar.CalendarActivity
import com.xiaoyv.bangumi.ui.feature.login.LoginActivity
import com.xiaoyv.bangumi.ui.feature.musmme.MusumeActivity
import com.xiaoyv.bangumi.ui.media.detail.MediaDetailActivity
import com.xiaoyv.bangumi.ui.profile.edit.EditProfileActivity
import com.xiaoyv.blueprint.constant.NavKey
import com.xiaoyv.blueprint.kts.open

/**
 * Class: [RouteHelper]
 *
 * @author why
 * @since 11/25/23
 */
object RouteHelper {

    fun jumpCalendar() {
        ActivityUtils.startActivity(CalendarActivity::class.java)
    }

    fun jumpLogin() {
        ActivityUtils.startActivity(LoginActivity::class.java)
    }

    fun jumpRobot() {
        ActivityUtils.startActivity(MusumeActivity::class.java)
    }

    fun jumpHome() {
        ActivityUtils.startActivity(HomeActivity::class.java)
    }

    fun jumpEditProfile() {
        ActivityUtils.startActivity(EditProfileActivity::class.java)
    }

    fun jumpMediaDetail(mediaId: String, mediaType: String? = null) {
        MediaDetailActivity::class.open(
            bundleOf(
                NavKey.KEY_STRING to mediaId,
                NavKey.KEY_STRING_SECOND to mediaType
            )
        )
    }
}