package com.db.dbapp.mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.db.dbapp.model.Item;
import com.db.dbapp.model.Producto;
import com.db.dbapp.model.dtos.ItemDto;

public class ItemMapper {

    public static Item toItem(Item item, ItemDto itemDto, Producto producto) {
	item.setCantidad(itemDto.getCantidad());
	item.setPrecioUnitario(itemDto.getPrecioUnitario());
	item.setProducto(producto);
	return item;
    }

    public static ItemDto toItemDto(ItemDto itemDto, Item item) {
	itemDto.setId(item.getId());
	itemDto.setCantidad(item.getCantidad());
	itemDto.setPrecioUnitario(item.getPrecioUnitario());
	itemDto.setProducto(item.getProducto().getDescripcion());
	itemDto.setIdProducto(item.getProducto().getId());
	itemDto.setCodigoProducto(item.getProducto().getCodigo());
	return itemDto;
    }

    public static List<ItemDto> toItemDtoList(Collection<Item> items) {
	return items == null ? new ArrayList<>()
		: items.stream().map(item -> ItemMapper.toItemDto(new ItemDto(), item)).collect(Collectors.toList());
    }

}
