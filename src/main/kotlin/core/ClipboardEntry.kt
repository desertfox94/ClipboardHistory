package core

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable
import java.util.Date

class ClipboardEntry constructor(val mimeType : String, val humanPresentableName : String, var data : Any, val date : Date? = Date()) {

	constructor (flavor : DataFlavor, data : Any) : this(flavor.mimeType, flavor.humanPresentableName, data) {
	}
	
	override operator fun equals(other: Any?): Boolean {
		if (other is ClipboardEntry)
			return data != null && data.equals((other as ClipboardEntry).data);
		return false;
	}
	
	fun getTransferable() : Transferable {
		return GenericTransferable(mimeType, humanPresentableName, data);
	}
	
}

class GenericTransferable(val mimeType : String, val humanPresentableName : String, val data : Any) : Transferable {
	
	constructor (flavor : DataFlavor, data : Any) : this(flavor.mimeType, flavor.humanPresentableName, data) {
	}
	
	override fun getTransferData(flavor: DataFlavor?): Any? {
		return data;
	}

	override fun isDataFlavorSupported(flavor: DataFlavor?): Boolean {
		return flavor != null && mimeType.equals(flavor.mimeType);
	}

	override fun getTransferDataFlavors(): Array<out DataFlavor>? {
		return arrayOf(DataFlavor(mimeType, humanPresentableName));
	}
}