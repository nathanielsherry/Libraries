package swidget.icons;

import javax.swing.ImageIcon;

public enum StockIcon {

	CHOOSE_OK,
	CHOOSE_CANCEL,
	
	ACTION_REFRESH,
	MENU_MAIN,
	MENU_SETTINGS,
	
	BADGE_INFO,
	BADGE_WARNING,
	BADGE_HELP,
	BADGE_ERROR,
	
	DEVICE_COMPUTER,
	DEVICE_HARDDISK,
	DEVICE_MONITOR,
	DEVICE_CAMERA,
	DEVICE_PRINTER,
	
	DOCUMENT_EXPORT,
	DOCUMENT_IMPORT,
	DOCUMENT_NEW,
	DOCUMENT_OPEN,
	DOCUMENT_SAVE_AS,
	DOCUMENT_SAVE,
	
	EDIT_CUT,
	EDIT_COPY,
	EDIT_PASTE,
	EDIT_ADD,
	EDIT_CLEAR,
	EDIT_REMOVE,
	EDIT_DELETE,
	EDIT_REDO,
	EDIT_UNDO,
	EDIT_EDIT,
	EDIT_SORT_ASC,
	EDIT_SORT_DES,
	
	FIND,
	FIND_REPLACE,
	FIND_SELECT_ALL,
	
	GO_BOTTOM,
	GO_DOWN,
	GO_FIRST,
	GO_LAST,
	GO_NEXT,
	GO_PREVIOUS,
	GO_TOP,
	GO_UP,
	
	MIME_PDF,
	MIME_RASTER,
	MIME_SVG,
	MIME_TEXT,
	
	MISC_ABOUT,
	MISC_PREFERENCES,
	MISC_PROPERTIES,
	MISC_EXECUTABLE,
	MISC_LOCKED,
	
	PLACE_DESKTOP,
	PLACE_FOLDER_OPEN,
	PLACE_FOLDER,
	PLACE_FOLDER_NEW,
	PLACE_HOME,
	PLACE_REMOTE,
	PLACE_TRASH,
	
	WINDOW_CLOSE,
	WINDOW_NEW,
	WINDOW_TAB_NEW,
	
	ZOOM_BEST_FIT,
	ZOOM_IN,
	ZOOM_ORIGINAL,
	ZOOM_OUT

	
	
	;
	
	@Override
	public String toString()
	{
		return this.name().replace("_", "-").toLowerCase();
	}
	
	public String toIconName()
	{
		return toString();
	}
	
	public ImageIcon toImageIcon(IconSize size)
	{
		return IconFactory.getImageIcon(toString(), size);
	}
	
	public ImageIcon toMenuIcon()
	{
		return IconFactory.getImageIcon(toString(), IconSize.BUTTON);
	}
	
}
