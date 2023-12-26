package com.xiaoyv.bangumi.ui.feature.setting.ui

import android.view.Menu
import android.view.MenuItem
import com.xiaoyv.bangumi.databinding.ActivitySettingUiBinding
import com.xiaoyv.blueprint.base.binding.BaseBindingActivity
import com.xiaoyv.common.config.GlobalConfig
import com.xiaoyv.common.currentApplication
import com.xiaoyv.common.helper.ConfigHelper
import com.xiaoyv.common.kts.initNavBack
import com.xiaoyv.common.kts.showConfirmDialog

/**
 * Class: [UiConfigActivity]
 *
 * @author why
 * @since 12/17/23
 */
class UiConfigActivity : BaseBindingActivity<ActivitySettingUiBinding>() {

    override fun initView() {
        binding.toolbar.initNavBack(this)
    }

    override fun initData() {
        binding.settingImageAnimation.bindBoolean(this, ConfigHelper::isImageAnimation)
        binding.settingImageCompress.bindBoolean(this, ConfigHelper::isImageCompress)
        binding.settingGridAnimation.bindBoolean(this, ConfigHelper::isAdapterAnimation)
        binding.settingDynamicTheme.bindBoolean(this, ConfigHelper::isDynamicTheme, onChange = {
            currentApplication.recreateAllActivity()
        })
        binding.settingFilterDelete.bindBoolean(this, ConfigHelper::isFilterDeleteComment)
        binding.settingBreakUp.bindBoolean(this, ConfigHelper::isFilterBreakUpComment)
        binding.settingEpSplit.bindBoolean(this, ConfigHelper::isSplitEpList)
        binding.settingSmoothFont.bindBoolean(this, ConfigHelper::isSmoothFont, onChange = {
            currentApplication.recreateAllActivity()
        })
        binding.settingFirstTab.bindInt(
            activity = this,
            property = ConfigHelper::homeDefaultTab,
            names = listOf("第1个", "第2个", "第3个", "第4个", "第5个"),
            values = listOf(0, 1, 2, 3, 4)
        )

        binding.settingCenterTab.bindInt(
            activity = this,
            property = ConfigHelper::centerTabType,
            names = listOf("排行榜", "追番进度"),
            values = listOf(GlobalConfig.PAGE_RANK, GlobalConfig.PAGE_PROCESS)
        )
    }

    override fun initListener() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add("提示")
            .setOnMenuItemClickListener {
                showConfirmDialog(message = "部分设置需要重启后生效", cancelText = null)
                true
            }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.initNavBack(this)
        return super.onOptionsItemSelected(item)
    }
}