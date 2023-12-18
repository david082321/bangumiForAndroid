package com.xiaoyv.bangumi.ui.media.detail.overview.binder

import android.content.Context
import android.content.res.ColorStateList
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.chad.library.adapter.base.BaseMultiItemAdapter
import com.xiaoyv.bangumi.R
import com.xiaoyv.bangumi.databinding.FragmentOverviewEpBinding
import com.xiaoyv.bangumi.databinding.FragmentOverviewEpItemBinding
import com.xiaoyv.bangumi.ui.media.detail.overview.OverviewAdapter
import com.xiaoyv.common.api.parser.entity.MediaDetailEntity
import com.xiaoyv.common.config.annotation.InterestType
import com.xiaoyv.common.helper.callback.IdDiffItemCallback
import com.xiaoyv.common.helper.callback.RecyclerItemTouchedListener
import com.xiaoyv.common.kts.CommonColor
import com.xiaoyv.common.kts.GoogleAttr
import com.xiaoyv.common.kts.forceCast
import com.xiaoyv.common.kts.inflater
import com.xiaoyv.common.kts.setOnDebouncedChildClickListener
import com.xiaoyv.common.kts.tint
import com.xiaoyv.widget.binder.BaseQuickBindingHolder
import com.xiaoyv.widget.binder.BaseQuickDiffBindingAdapter
import com.xiaoyv.widget.kts.getAttrColor
import com.xiaoyv.widget.kts.subListLimit

/**
 * Class: [OverviewEpBinder]
 *
 * @author why
 * @since 11/30/23
 */
class OverviewEpBinder(
    private val touchedListener: RecyclerItemTouchedListener,
    private val clickItemListener: (MediaDetailEntity.MediaProgress) -> Unit,
) : BaseMultiItemAdapter.OnMultiItemAdapterListener<OverviewAdapter.Item, BaseQuickBindingHolder<FragmentOverviewEpBinding>> {

    /**
     * 最大显示 48 个
     */
    private val subSize = 24

    private val itemAdapter by lazy {
        ItemEpAdapter().apply {
            setOnDebouncedChildClickListener(R.id.item_ep, block = clickItemListener)
        }
    }

    override fun onBind(
        holder: BaseQuickBindingHolder<FragmentOverviewEpBinding>,
        position: Int,
        item: OverviewAdapter.Item?,
    ) {
        item ?: return
        holder.binding.tvEpMyProgress.text = String.format("%s/10", 1)
        holder.binding.tvTitleEp.title = item.title

        item.entity.forceCast<MediaDetailEntity>().apply {
            holder.binding.tvEpMyProgress.isVisible =
                (collectState.interest != InterestType.TYPE_UNKNOWN && collectState.interest != InterestType.TYPE_WISH)

            holder.binding.tvEpMyProgress.text =
                String.format("我的完成度：%d/%d", myProgress, totalProgress)
            itemAdapter.submitList(progressList.subListLimit(subSize))
        }
    }

    override fun onCreate(
        context: Context,
        parent: ViewGroup,
        viewType: Int,
    ): BaseQuickBindingHolder<FragmentOverviewEpBinding> {
        val binding = FragmentOverviewEpBinding.inflate(context.inflater, parent, false)
        binding.rvEp.adapter = itemAdapter
        binding.rvEp.addOnItemTouchListener(touchedListener)
        return BaseQuickBindingHolder(binding)
    }

    /**
     * Class: [ItemEpAdapter]
     *
     * @param selectedMode 是否为完成格子时的选取模式
     */
    internal class ItemEpAdapter(
        val selectedMode: Boolean = false,
        var selectIndex: Int = -1,
    ) : BaseQuickDiffBindingAdapter<MediaDetailEntity.MediaProgress,
            FragmentOverviewEpItemBinding>(IdDiffItemCallback()) {

        override fun onBindViewHolder(
            holder: BaseQuickBindingHolder<FragmentOverviewEpItemBinding>,
            position: Int,
            item: MediaDetailEntity.MediaProgress?,
            payloads: List<Any>,
        ) {
            super.onBindViewHolder(holder, position, item, payloads)
            payloads.forEach {
                if (it == PAYLOAD_REFRESH_COLOR) {
                    refreshColor(position, holder)
                }
            }
        }

        override fun onBindViewHolder(
            holder: BaseQuickBindingHolder<FragmentOverviewEpItemBinding>,
            position: Int,
            item: MediaDetailEntity.MediaProgress?,
        ) {
            super.onBindViewHolder(holder, position, item)

            // 完成进度复用时的UI逻辑
            refreshColor(position, holder)
        }

        override fun BaseQuickBindingHolder<FragmentOverviewEpItemBinding>.converted(item: MediaDetailEntity.MediaProgress) {
            binding.tvEp.text = item.number

            // 完成进度复用时，不执行这里逻辑
            if (selectedMode.not()) when {
                item.isRelease -> {
                    binding.tvEp.setTextColor(context.getAttrColor(GoogleAttr.colorOnPrimaryContainer))
                    binding.tvEp.backgroundTintList = ColorStateList.valueOf(
                        context.getAttrColor(GoogleAttr.colorPrimaryContainer)
                    )
                }

                else -> {
                    binding.tvEp.setTextColor(context.getAttrColor(GoogleAttr.colorOnSurface))
                    binding.tvEp.backgroundTintList = ColorStateList.valueOf(
                        context.getAttrColor(GoogleAttr.colorSurfaceContainer)
                    )
                }
            }
        }

        private fun refreshColor(
            position: Int,
            holder: BaseQuickBindingHolder<FragmentOverviewEpItemBinding>,
        ) {
            if (selectedMode) {
                if (position <= selectIndex) {
                    holder.binding.tvEp.setTextColor(context.getAttrColor(GoogleAttr.colorOnPrimarySurface))
                    holder.binding.tvEp.backgroundTintList =
                        context.getColor(CommonColor.save_collect).tint
                } else {
                    holder.binding.tvEp.setTextColor(context.getAttrColor(GoogleAttr.colorOnSurface))
                    holder.binding.tvEp.backgroundTintList =
                        context.getAttrColor(GoogleAttr.colorSurfaceContainer).tint
                }
            }
        }

        companion object {
            const val PAYLOAD_REFRESH_COLOR = 1
        }
    }
}