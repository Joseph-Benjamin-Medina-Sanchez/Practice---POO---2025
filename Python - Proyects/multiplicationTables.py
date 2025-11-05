print("-----WELCOME_TO_THE_MULTIPLICATION_TABLES-----")
print("-----Tables_available_from_(1)_to_(12)-----") 
print("")
option = True
while option:
    valid_number = True
    while valid_number:
        multiplicative_value = int(input("Enter which number you want to see the tables of --> "))
        if multiplicative_value < 1 or multiplicative_value > 12:
            print("Make sure your value is between 1 and 12") 
        else:
            valid_number = False


    print("You have selected", multiplicative_value)
    print("—" * 16)
    for i in range(1, 13, 1):
        print(f"| {multiplicative_value:2} * {i:2} = {multiplicative_value * i} |")
    print("—" * 16)
    print("Do you wish to continue?")
    print("Choose an option:")
    print("(1) --> Yes")
    print("(2) --> No")
    option = input("--> ").strip()
    if option != "1":
        print("Thanks for using us - JM")
        break
