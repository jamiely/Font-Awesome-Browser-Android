ICON=../ic_launcher-web.png
OUTFILE=ic_launcher.png
OUTPATH=../res/drawable-
function resize {
  convert -resize ${1}x${1} $ICON $OUTPATH${2}dpi/$OUTFILE
}
resize 72 h
resize 36 l
resize 48 m
resize 96 xh
