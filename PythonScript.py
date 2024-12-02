from openai import OpenAI
import os
import sys
import subprocess
import shutil
import time
import csv

client = OpenAI(
    api_key='YOUR KEY',
)

# Global variables initializing
token_usage = 0  # Token accumulator initialization
start_time = time.time()  # Script execution timer initialization

# Function to identify the gpt model from the model_option parameter
def identify_gpt_model(model_option):
    
    match model_option:
        case 1:
            model = "gpt-3.5-turbo-0125"
            model_class_name = "gpt35"
        case 2:
            model = "gpt-4-turbo-2024-04-09"
            model_class_name = "gpt4turbo"
        case 3:
            model = "gpt-4o-2024-05-13"
            model_class_name = "gpt4o"
        case _:
            model = "gpt-3.5-turbo-0125"
            model_class_name = "gpt35"
    
    return model, model_class_name

# Function to create class name
def create_class_name(file_name):
    
    class_name = prompt_versions + '_' + model_class_name + '_' + run_directory + '_' + file_name + 'Test'
    
    return class_name

# Function to clear a directory
def clear_directory(directory_path):
    
    if os.path.exists(directory_path):
        shutil.rmtree(directory_path)
    os.makedirs(directory_path, exist_ok=True)

# Function to clear multiple directories
def clear_multiple_directories(base_directory, run_directory, directories):
    
    for directory in directories:
        dir_path = os.path.join(base_directory, f'gpt/{run_directory}/{directory}')
        clear_directory(dir_path)

# Function to creates the first prompt
def build_prompt(file, class_name):
    
    text_prompt = ""
    with open(f'{base_directory}/gpt/user_prompt.txt', 'r', encoding='utf-8') as f:
        text_prompt += f.read()
    
    text_prompt = text_prompt.replace('<<class_name>>', class_name)
    
    text_prompt += "\n*****\n"
    with open(f'{application_code_directory}/{file}', 'r', encoding='utf-8') as f:
        text_prompt += f.read()
    
    text_prompt += "\n*****\n#####\n"
    with open(f'{base_directory}/gpt/swagger.json', 'r', encoding='utf-8') as f:
        text_prompt += f.read()
    text_prompt += "\n#####\n"
    
    return text_prompt

# Generates tests from a prompt
def generate(user_prompt, system_prompt, model):
    
    global token_usage
    completion = client.chat.completions.create(
        model = model,
        messages = [
            {"role": "system", "content": system_prompt},
            {"role": "user", "content": user_prompt}
        ],
        temperature = 0.7
    )
    token_usage += completion.usage.total_tokens
    
    return completion.choices[0].message.content

# Saves the metrics (tokens and time)
def save_metrics(base_directory, run_directory):
    
    total_time = time.time() - start_time  # Calculates the total execution time
    print(token_usage)
    print(total_time)
    
    with open(os.path.join(base_directory, f'{base_directory}/gpt/{run_directory}_metrics.csv'), 'w', newline='') as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow(['Total Tokens Used', 'Total Execution Time (s)'])
        writer.writerow([token_usage, total_time])

# Funcion to excludes ''' characters that gpt places in the code
def clean_file(file_path):
    
    with open(file_path, 'r') as file:
        content = file.read()
    
    new_content = content.replace("```java", "").replace("```", "")
    with open(file_path, 'w') as file:
        file.write(new_content)

# Function to copies the files to the project folder and run with maven
def execute_maven_process(test_file_path, mvn_project_directory, test_project_directory, mode):
    
    clear_directory(test_project_directory)
    
    # Copy test file to test project directory
    shutil.copy(test_file_path, test_project_directory)
    
    # Change the context to the project directory
    os.chdir(mvn_project_directory)
    
    # Run maven according mode (compilation or execution test)
    if mode == "comp":
        result = subprocess.run(['mvn', 'clean', 'compile', 'test-compile'], capture_output=True, text=True)
    elif mode == "exec":
        result = subprocess.run(['mvn', 'clean', 'compile', 'test-compile', 'test'], capture_output=True, text=True)
    
    return result

# Function to creats prompt after compilation failed
def build_prompt_comp(test_file_path):
    
    test_file_dir = os.path.dirname(test_file_path)
    test_file_name = os.path.basename(test_file_path)
    
    text_prompt = ""
    with open(f'{base_directory}/gpt/user_prompt_after_comp.txt', 'r', encoding='utf-8') as f:
        text_prompt += f.read()
    
    text_prompt += "\n*****\n"
    with open(f'{test_file_path}', 'r', encoding='utf-8') as f:
        text_prompt += f.read()
    
    text_prompt += "\n*****\n#####\n"
    with open(f'{test_file_dir}/{test_file_name}._mvn_output.txt', 'r', encoding='utf-8') as f:
        text_prompt += f.read()
    text_prompt += "\n#####\n"
    
    return text_prompt

# Function to saves files whit maven results
def save_files_messages_maven_result(test_file_path, result_maven_process):
    
    test_file_dir = os.path.dirname(test_file_path)
    test_file_name = os.path.basename(test_file_path)
    
    output_file_path = os.path.join(test_file_dir, f'{test_file_name}._mvn_output.txt')
    with open(output_file_path, 'w') as output_file:
        output_file.write(result_maven_process.stdout)
    
    return output_file_path

# Function to compilation (steps 2 and 3) and execution (steps 5 and 6)
def comp_exec_steps(step_number, test_file_path, base_directory, run_directory, class_name, mvn_project_directory, test_project_directory, dest_directory):
    
    if step_number == 2:
        step_directory = os.path.join(base_directory, f'gpt/{run_directory}/step2')
        mode = "comp"
    elif step_number == 3:
        step_directory = os.path.join(base_directory, f'gpt/{run_directory}/step3')
        mode = "comp"
    elif step_number == 5:
        step_directory = os.path.join(base_directory, f'gpt/{run_directory}/step5')
        mode = "exec"
    elif step_number == 6:
        step_directory = os.path.join(base_directory, f'gpt/{run_directory}/step6')
        mode = "exec"

    user_prompt = build_prompt_comp(test_file_path)
    test_file_name = os.path.basename(test_file_path)
    with open(os.path.join(step_directory, f'prompt_{test_file_name}.txt'), 'w') as file_prompt:
        file_prompt.write(user_prompt)
    
    system_prompt = " "
    with open(f'{base_directory}/gpt/system_prompt.txt', 'r', encoding='utf-8') as f:
        system_prompt = f.read()

    test_file_path = os.path.join(step_directory, f'{class_name}.java')
    with open(test_file_path, 'w') as file_java:
        file_java.write(generate(user_prompt, system_prompt, model))
    
    clean_file(test_file_path)
    
    result_maven_process = execute_maven_process(test_file_path, mvn_project_directory, test_project_directory, mode)
    print("Mavem compilation result: " + str(result_maven_process.returncode))

    if result_maven_process.returncode == 0:
        shutil.copy(test_file_path, dest_directory)
    else:
        save_files_messages_maven_result(test_file_path, result_maven_process)

    return result_maven_process.returncode, test_file_path

# Function to creates the initial prompt and first test file version
def compilation_step_one(base_directory, run_directory, file, mvn_project_directory, test_project_directory, comp_directory):
    
    step_directory = os.path.join(base_directory, f'gpt/{run_directory}/step1')

    user_prompt = build_prompt(file, class_name)
    with open(os.path.join(base_directory, f'gpt/{run_directory}/prompts/prompt_{class_name}.txt'), 'w') as file_prompt:
        file_prompt.write(user_prompt)
    
    system_prompt = " "
    with open(f'{base_directory}/gpt/system_prompt.txt', 'r', encoding='utf-8') as f:
        system_prompt = f.read()

    test_file_path = os.path.join(step_directory, f'{class_name}.java')
    with open(test_file_path, 'w') as file_java:
        file_java.write(generate(user_prompt, system_prompt, model))
    
    clean_file(test_file_path)

    mode = "comp"
    result_maven_process = execute_maven_process(test_file_path, mvn_project_directory, test_project_directory, mode)
    print("Mavem compilation result: " + str(result_maven_process.returncode))

    if result_maven_process.returncode == 0:
        shutil.copy(test_file_path, comp_directory)
    else:
        save_files_messages_maven_result(test_file_path, result_maven_process)

    return result_maven_process.returncode, test_file_path

# Function to execute test file for the first time (step 4)
def execution_step_four(base_directory, run_directory, test_file_path, mvn_project_directory, test_project_directory, comp_directory):
    
    step_directory = os.path.join(base_directory, f'gpt/{run_directory}/step4')

    # Copy compiled file to step 4 folder
    shutil.copy(test_file_path, step_directory)
    test_file_path = os.path.join(step_directory, os.path.basename(test_file_path))
    
    mode = "exec"
    result_maven_process = execute_maven_process(test_file_path, mvn_project_directory, test_project_directory, mode)
    print("Mavem compilation result: " + str(result_maven_process.returncode))

    if result_maven_process.returncode == 0:
        shutil.copy(test_file_path, exec_directory)
    else:
        save_files_messages_maven_result(test_file_path, result_maven_process)

    return result_maven_process.returncode, test_file_path

# main
if __name__ == '__main__':
    
    # Get the command line parameters
    base_directory = sys.argv[1]
    mvn_project_directory = sys.argv[2]
    test_project_directory = sys.argv[3]
    prompt_versions = sys.argv[4]
    run_directory = sys.argv[5]
    model_option = int(sys.argv[6])
    
    print("\nStart execution")

    # Identify and set the GPT Model to OpenAI function and class name identification
    model, model_class_name = (identify_gpt_model(model_option))
    print("\nModel: " + model + " and model to class name: " + model_class_name)

    # Clear directories where the tests will be generated
    directories_to_clear = ['prompts', 'step1', 'step2', 'step3', 'step4', 'step5', 'step6', 'comp', 'exec']
    clear_multiple_directories(base_directory, run_directory, directories_to_clear)

    # Initialize directories to compilation and execution steps
    application_code_directory = os.path.join(base_directory, 'gpt/applicationcode')
    comp_directory = os.path.join(base_directory, f'gpt/{run_directory}/comp')
    exec_directory = os.path.join(base_directory, f'gpt/{run_directory}/exec')

    # Start iteraction do create test set
    files = os.listdir(application_code_directory)
    total_files = len(files)
    for index, file in enumerate(files, start = 1):

        print(f"\nApplication code file name: {file} ({index}/{total_files})")

        result_maven = 1 # Indicates whether the compilation was successful (0) or failed (1)
        max_generate_iteractions = 0 # Counter for generation and compilation attempts
        execute_test_file = True # Indicates whether the max compilation attempts was successful or not
        
        while (result_maven == 1 and max_generate_iteractions < 1):

            # Creates the class name from promtp version, model, run number and file name
            file_name = os.path.splitext(file)[0]
            class_name = create_class_name(file_name)
            
            # Creating the initial prompt and first test file version
            print("\n---------Compilation steps-----------")
            print("------------------1------------------")
            result_maven, test_file_path = compilation_step_one(base_directory, run_directory, file, mvn_project_directory, test_project_directory, comp_directory)

            if result_maven == 1:
                # Creating the second prompt and second test file version
                print("------------------2------------------")
                step_number = 2
                dest_directory = comp_directory
                result_maven, test_file_path = comp_exec_steps(step_number, test_file_path, base_directory, run_directory, class_name, mvn_project_directory, test_project_directory, dest_directory)

                if result_maven == 1:
                    # Creating the third prompt and third test file version
                    print("------------------3------------------")
                    step_number = 3
                    dest_directory = comp_directory
                    result_maven, test_file_path = comp_exec_steps(step_number, test_file_path, base_directory, run_directory, class_name, mvn_project_directory, test_project_directory, dest_directory)
            
            max_generate_iteractions += 1
        
        if result_maven != 0:  # If still failed after max attempts
            execute_test_file = False

        result_maven = 1
        while (result_maven == 1 and execute_test_file):

            # Run the first execution on compiled file test
            print("\n----------Execution steps------------")
            print("------------------4------------------")
            result_maven, test_file_path = execution_step_four(base_directory, run_directory, test_file_path, mvn_project_directory, test_project_directory, comp_directory)

            if result_maven == 1:
                # Run the second execution on compiled file test
                print("------------------5------------------")
                step_number = 5
                dest_directory = exec_directory
                result_maven, test_file_path = comp_exec_steps(step_number, test_file_path, base_directory, run_directory, class_name, mvn_project_directory, test_project_directory, dest_directory)

                if result_maven == 1:
                    # Run the third execution on compiled file test
                    print("------------------6------------------")
                    step_number = 6
                    dest_directory = exec_directory
                    result_maven, test_file_path = comp_exec_steps(step_number, test_file_path, base_directory, run_directory, class_name, mvn_project_directory, test_project_directory, dest_directory)
                    shutil.copy(test_file_path, exec_directory)
                    result_maven = 0

    save_metrics(base_directory, run_directory)