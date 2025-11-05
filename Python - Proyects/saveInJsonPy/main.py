from view.farm_simulator_view import FarmSimulatorView

def main():
    print("Welcome to the Chicken Farm Simulator")
    farmer_name = input("Please enter the farmer's name: ")
    view = FarmSimulatorView(farmer_name)
    view.run()

if __name__ == "__main__":
    main()
