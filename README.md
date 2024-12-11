# File Compression Application

A Java-based application to compress and decompress files using the Huffman coding algorithm. The project includes a user-friendly GUI to simplify file compression and decompression operations.

---

## Features
- Compress files using **Huffman Coding**.
- Decompress files back to their original form.
- Simple and intuitive **GUI** using Java Swing.
- Automatic naming for compressed (`filename-comp.txt`) and decompressed (`filename-decomp.txt`) files.

---

## Project Structure

### **Files**
1. **`HuffCompression.java`**  
   Implements the Huffman coding algorithm for file compression and decompression.
   - `compress(String sourcePath, String destPath)` – Compresses a file.
   - `decompress(String sourcePath, String destPath)` – Decompresses a file.

2. **`ByteNode.java`**  
   Utility class for building the Huffman tree.
   - Handles byte frequency and creates nodes for compression.

3. **`CompressorUI.java`**  
   GUI for file compression and decompression.
   - Home screen to choose between compression and decompression.
   - File and folder selection dialogs for source and destination paths.

4. **`Main.java`**  
   Entry point of the application.
   - Launches the `CompressorUI`.

---

## How to Run

### **Prerequisites**
- Java Development Kit (JDK) 8 or higher.
- An IDE (e.g., IntelliJ IDEA, Eclipse) or terminal for execution.

### **Steps**
1. Clone this repository:
   ```bash
   git clone https://github.com/your-repo-name/file-compression.git
