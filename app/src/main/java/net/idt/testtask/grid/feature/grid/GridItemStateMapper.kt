package net.idt.testtask.grid.feature.grid

import net.idt.testtask.domain.model.TextDomainModel

/**
 * Map [TextDomainModel] to [GridItemState]
 */
class GridItemStateMapper: (TextDomainModel) -> GridItemState {

    override fun invoke(domainModel: TextDomainModel): GridItemState = GridItemState(
        id = domainModel.id,
        title = domainModel.text,
        isSelected = false,
        isEditable = false
    )
}
