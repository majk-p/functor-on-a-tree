#!/bin/bash
SOURCE=${1:-"slides.md"} 
MARP_SOURCE="marp-${SOURCE}"
# Run coursier for the first time
coursier launch org.scalameta:mdoc_2.12:2.5.4 -- \
  --in $SOURCE \
  --out $MARP_SOURCE

OUTPUT_DIR="."
mkdir -p ${OUTPUT_DIR}/img
cp -r img/* ${OUTPUT_DIR}/img 2> /dev/null
DESTINATION=${2:-"${OUTPUT_DIR}/slides.html"}
echo "Running live reload, the content will be rendered to ${PWD}/${DESTINATION}"
echo "Open it in browser to see the results"

# Run marp in background mode
npx -y @marp-team/marp-cli@latest ${MARP_SOURCE} --allow-local-files --theme-set mp-theme.css --engine ./engine.js -o ${DESTINATION} --watch &

# Run coursier watch mode
coursier launch org.scalameta:mdoc_2.12:2.5.4 -- \
  --in $SOURCE \
  --out $MARP_SOURCE \
  --watch

