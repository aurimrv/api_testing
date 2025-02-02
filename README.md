Repository created for the paper **"LLM Prompt Engineering for Automated White-Box Integration Test Generation in REST APIs"**, submitted to the 5th International Workshop on Artificial Intelligence in Software Testing.

---

## Repository Structure

### 1. `PythonScript.py`
- **Description**: Main script that implements the generation and refinement of test suites using the OpenAI API.  
- **Objective**: Automates the creation and optimization of tests based on different prompt versions.

---

### 2. `EMB Projects`
- **Description**: Folder containing the EvoMaster Benchmark ([EMB](https://github.com/WebFuzzing/EMB)) projects.  
- **Modifications**:  
  - The configurations have been adapted to enable integration with `PythonScript.py`.

---

### 3. `Full Experiment Data`
- **Description**: Contains detailed tables with data from each test suite generation.  
- **Organization**:  
  - The results are organized by prompt version.  
  - Metrics include code coverage, token consumption, and execution time.

---

### 4. `Generated Tests`
- **Description**: Repository of all files generated during the execution of EvoMaster and `PythonScript.py`.  
- **Subfolders sample**:  
  1. **EvoMaster**:  
     - **Location**: `market-test-set/evomaster/`  
     - **Description**: Contains test and coverage files for each of the 10 EvoMaster executions using seeds 01 to 10.  
  2. **OpenAI GPT (PythonScript)**:  
     - **Location**: `market-test-set/gpt/v0/gpt35/`  
     - **Description**: Contains test and coverage files generated by `PythonScript.py` using the **GPT-3.5 Turbo** model and prompt version **v0**.  
     - **Executions**: Results from 3 runs (run01, run02, and run03) for each configuration.

---

## Data Location for Tables and Research Questions

### **RQ1**
1. **Complete Prompt Versions**  
   - **Example:** `market-test-set/gpt/v0/`  
   - For each prompt version (v0 to v3) of each project, there are templates for the *user prompts* and *system prompts* used.  
   - **Example Path:** `market-test-set/gpt/v0/gpt35/run01/prompts/`  
     - **Content:** Prompts created for each file of the REST API application.

2. **Generated Test Suites**  
   - **Example:** `market-test-set/gpt/v0/gpt35/run01/exec/`  
   - The `exec` folder contains the final version of the tests used to generate the data presented in the `Full Experiment Data` folder and in Table I of the paper.

---

### **RQ2**
- **Data Related to Table II in the Paper**  
  - **Examples:**  
    - `market-test-set/gpt/v0/coverage_three_runs/`  
    - `market-test-set/gpt/v0/model_combinations/`  
  - These folders contain reports with combinations of test suites generated by `PythonScript.py`.

---

### **RQ3**
- **Data Related to Table III in the Paper**
  - **Example:** `market-test-set/gpt/v0/gpt35/run01/exec/`  
   - The `exec` folder contains the final version of the tests used to calculate the number of test cases.
---

### **RQ4**
- **Data Related to Table IV in the Paper**  
  - **Example:** `market-test-set/evomaster/evomaster-combination-report/`  
  - Contains reports with combinations of test suites generated by `PythonScript.py` and EvoMaster.

---
