#!/bin/bash
SOURCE=${1:-"slides.md"}
OUTPUT_DIR="."
mkdir -p ${OUTPUT_DIR}
DESTINATION=${2:-"${OUTPUT_DIR}/slides.pdf"}
echo "The content will be rendered to ${PWD}/${DESTINATION}"
npx -y @marp-team/marp-cli@latest ${SOURCE} --allow-local-files --theme-set mp-theme.css --engine ./engine.js -o ${DESTINATION}
