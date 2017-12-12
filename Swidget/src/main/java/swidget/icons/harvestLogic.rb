

def harvestIcon(path, folder, name, newname, elementary=true)

	puts name

	if elementary then
		harvestIconAtSize(path, folder, name, newname, 48)
		harvestIconAtSize(path, folder, name, newname, 32, 48)
		harvestIconAtSize(path, folder, name, newname, 24)
		harvestIconAtSize(path, folder, name, newname, 16, 24)
		#`rsvg "#{path}/#{folder}/48/#{name}.svg" "./48/#{newname}.png"`
		#`rsvg -w32 -h32 "#{path}/#{folder}/48/#{name}.svg" "./32/#{newname}.png"`
		#`rsvg "#{path}/#{folder}/24/#{name}.svg" "./24/#{newname}.png"`
		#`rsvg -w16 -h16 "#{path}/#{folder}/24/#{name}.svg" "./16/#{newname}.png"`
	else
		if (File.exists? "#{path}/48x48/#{folder}/#{name}.png") then
			`cp "#{path}/48x48/#{folder}/#{name}.png" "./48/#{newname}.png"`
		else
			`rsvg "#{path}/scalable/#{folder}/#{name}.svg" "./48/#{newname}.png"`
		end
		`cp "#{path}/32x32/#{folder}/#{name}.png" "./32/#{newname}.png"`
		`cp "#{path}/24x24/#{folder}/#{name}.png" "./24/#{newname}.png"`
		`cp "#{path}/16x16/#{folder}/#{name}.png" "./16/#{newname}.png"`
	end
	
	puts ""
	
end


#assumes elementary icon
def harvestIconAtSize(path, folder, name, newname, size, fallbackSize=nil)

	if File.exist?("#{path}/#{folder}/#{size}/#{name}.svg")
		`rsvg "#{path}/#{folder}/#{size}/#{name}.svg" "./#{size}/#{newname}.png"`
	elsif fallbackSize != nil
		`rsvg -w#{size} -h#{size} "#{path}/#{folder}/#{fallbackSize}/#{name}.svg" "./#{size}/#{newname}.png"`
	end

end

def harvestCustom(name, newname)

	`rsvg -w48 -h48 "./custom/#{name}.svg" "./48/#{newname}.png"`
	`rsvg -w32 -h32 "./custom/#{name}.svg" "./32/#{newname}.png"`
	`rsvg -w24 -h24 "./custom/#{name}.svg" "./24/#{newname}.png"`
	`rsvg -w16 -h16 "./custom/#{name}.svg" "./16/#{newname}.png"`

end
