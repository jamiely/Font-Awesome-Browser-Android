
STDIN.lines.each do |line|
  if line =~ /^\.icon-(.+):before.+{ content: "\\(.+)"; }/
    dec = $2.hex
    puts "#{$1},0x#{$2},#{dec}"
  end
end

