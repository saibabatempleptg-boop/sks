with open('lovable_routes.js') as f:
    text = f.read()

import re

# Let's extract all sections and strings
# Write a formatted dump
with open('extracted_details.txt', 'w') as out:
    # Find all image URLs and alts
    images = re.findall(r'\{src:([a-zA-Z0-9_]+)\.url,alt:`([^`]+)`\}', text)
    out.write(f"Images: {len(images)}\n")
    for img in images:
        out.write(f"  Img: {img}\n")

    # Find timings
    out.write("\n--- TIMINGS & OBJECTS ---\n")
    matches = re.findall(r'(\[(?:\{[^}]+\},?)+\])', text)
    for m in matches:
        if any(k in m for k in ['darshan', 'timings', 'abhishek', 'Bhajan', 'Homams', 'Puritigadda', 'guruvar']):
            out.write(m + "\n\n")

    # Find full text lines
    out.write("\n--- ALL BACKTICK STRINGS ---\n")
    bt = re.findall(r'`([^`]{5,400})`', text)
    for b in bt:
        out.write(b + "\n")

print("Dumped extracted_details.txt")
