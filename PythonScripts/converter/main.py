import os
from pdf2image import convert_from_path

# Specify the path to the PDF file and the output folder
pdf_path = './ChessBot.pdf'
output_folder = './out'

# Ensure the output folder exists
os.makedirs(output_folder, exist_ok=True)

# Path to the poppler binaries
poppler_path = '/usr/local/bin'  # Update this path to where poppler is installed

# Convert PDF to images
pages = convert_from_path(pdf_path, poppler_path=poppler_path)

# Save each page as a JPG file
for i, page in enumerate(pages):
    output_path = os.path.join(output_folder, f'page_{i + 1}.jpg')
    page.save(output_path, 'JPEG')

print(f'All pages have been converted and saved to {output_folder}')