import zipfile
import os

BPEL_PROCESS_DIR = "D:\\PROJECTS\\Java\\globalbooks-soa\\bpel"
PROCESS_NAME = "PlaceOrderProcess"

output_zip_path = os.path.join(BPEL_PROCESS_DIR, f"{PROCESS_NAME}.zip")

with zipfile.ZipFile(output_zip_path, 'w', zipfile.ZIP_DEFLATED) as zipf:
    for root, dirs, files in os.walk(os.path.join(BPEL_PROCESS_DIR, 'processes')):
        for file in files:
            file_path = os.path.join(root, file)
            zipf.write(file_path, os.path.relpath(file_path, BPEL_PROCESS_DIR))
    for root, dirs, files in os.walk(os.path.join(BPEL_PROCESS_DIR, 'deploy')):
        for file in files:
            file_path = os.path.join(root, file)
            zipf.write(file_path, os.path.relpath(file_path, BPEL_PROCESS_DIR))
    for root, dirs, files in os.walk(os.path.join(BPEL_PROCESS_DIR, 'wsdl')):
        for file in files:
            file_path = os.path.join(root, file)
            zipf.write(file_path, os.path.relpath(file_path, BPEL_PROCESS_DIR))

print(f"BPEL process packaged as {output_zip_path}")
print(f"Please deploy {PROCESS_NAME}.zip to your Apache ODE deployment directory (e.g., apache-ode/webapps/ode/WEB-INF/processes)")
