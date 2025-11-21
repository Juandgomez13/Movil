import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import LoginScreen from './LoginScreen';
import HomeScreen from './HomeScreen';
import UsersScreen from './UsersScreen';
import ProductsScreen from './ProductsScreen';

const Stack = createNativeStackNavigator();

export default function App() {
    return (
        <NavigationContainer>
            <Stack.Navigator initialRouteName="Login">
                <Stack.Screen name="Login" component={LoginScreen} options={{ headerShown: false }} />
                <Stack.Screen name="Home" component={HomeScreen} options={{ title: 'Menú principal' }} />
                <Stack.Screen name="Users" component={UsersScreen} options={{ title: 'Gestión de Usuarios' }} />
                <Stack.Screen name="Products" component={ProductsScreen} options={{ title: 'Productos' }} />
            </Stack.Navigator>
        </NavigationContainer>
    );
}
                



