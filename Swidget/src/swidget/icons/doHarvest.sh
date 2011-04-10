#!/bin/sh

mkdir ./16/
mkdir ./24/
mkdir ./32/
mkdir ./48/

#ok/cancel
./harvest.rb actions dialog-apply choose-ok
./harvest.rb actions process-stop choose-cancel

#mime types
./harvest.rb mimes gnome-mime-application-pdf mime-pdf
./harvest.rb mimes x-office-drawing mime-svg
./harvest.rb mimes text-x-generic mime-text
./harvestOther.rb /usr/share/icons/gnome mimetypes image-x-generic mime-raster

#undo/redo
./harvest.rb actions edit-undo
./harvest.rb actions edit-redo

#cut/copy/paste
./harvestOther.rb /usr/share/icons/gnome actions edit-cut
./harvestOther.rb /usr/share/icons/gnome actions edit-copy
./harvestOther.rb /usr/share/icons/gnome actions edit-paste

#zoom controls
./harvestOther.rb /usr/share/icons/gnome actions zoom-in
./harvestOther.rb /usr/share/icons/gnome actions zoom-out
./harvestOther.rb /usr/share/icons/gnome actions zoom-original
./harvestOther.rb /usr/share/icons/gnome actions zoom-best-fit

#list/edit controls
./harvest.rb actions list-add edit-add
./harvest.rb actions edit-delete
./harvest.rb actions edit-clear
./harvest.rb actions gtk-edit edit-edit

#directional controls
./harvest.rb actions go-up
./harvest.rb actions go-down
./harvest.rb actions go-next
./harvest.rb actions go-previous
./harvest.rb actions go-top
./harvest.rb actions go-bottom
./harvest.rb actions go-first
./harvest.rb actions go-last

#document operations
./harvest.rb actions document-open
./harvest.rb actions document-save
./harvest.rb actions document-save-as
./harvest.rb actions document-new
./harvest.rb actions document-import
./harvest.rb actions document-export

#window/tab operations
./harvest.rb actions window-close window-close
./harvest.rb actions window-new window-new
./harvest.rb actions tab-new window-tab-new

#badges
./harvest.rb actions gtk-info badge-info
./harvest.rb status dialog-warning badge-warning
./harvest.rb actions help-hint badge-hint
./harvest.rb actions help-contents badge-help


#find/replace
./harvest.rb actions edit-find find
./harvest.rb actions edit-find-replace find-replace
./harvest.rb actions edit-select-all find-select-all

#devices
./harvest.rb devices camera-photo device-camera
./harvest.rb devices drive-harddisk device-harddisk
./harvest.rb devices video-display device-monitor
./harvest.rb devices video-display device-computer
./harvest.rb devices printer device-printer

#places
./harvest.rb places user-desktop place-desktop
./harvest.rb places folder place-folder
./harvest.rb places gnome-fs-directory-accept place-folder-open
./harvest.rb places folder-remote place-remote
./harvest.rb places user-trash place-trash
./harvest.rb places folder-home place-home

#misc
./harvest.rb actions help-about misc-about
./harvest.rb apps gnome-desktop-config misc-preferences
./harvest.rb actions document-properties misc-properties
./harvest.rb apps application-default-icon misc-executable


#sort
./harvestOther.rb /usr/share/icons/gnome actions view-sort-ascending edit-sort-asc
./harvestOther.rb /usr/share/icons/gnome actions view-sort-descending edit-sort-des
