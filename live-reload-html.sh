#!/bin/bash
SOURCE=${1:-"slides.md"}
OUTPUT_DIR="."
mkdir -p ${OUTPUT_DIR}/img
cp -r img/* ${OUTPUT_DIR}/img 2> /dev/null
DESTINATION=${2:-"${OUTPUT_DIR}/slides.html"}
echo "Running live reload, the content will be rendered to ${PWD}/${DESTINATION}"
echo "Open it in browser to see the results"
npx -y @marp-team/marp-cli@latest ${SOURCE} --allow-local-files --theme-set mp-theme.css --engine ./engine.js -o ${DESTINATION} --watch 
