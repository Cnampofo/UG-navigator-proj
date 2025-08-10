class UGCampusNavigator {
    constructor() {
        this.map = null;
        this.markers = [];
        this.currentRoute = null;
        this.userLocation = null;
        this.locations = [];
        this.currentLocationMarker = null;
        
        this.init();
    }
    
    async init() {
        try {
            await this.initializeMap();
            await this.loadLocations();
            this.setupEventListeners();
            this.hideLoading();
        } catch (error) {
            console.error('Error initializing app:', error);
            this.showError('Failed to initialize the application');
        }
    }
    
    initializeMap() {
        return new Promise((resolve) => {
            // Initialize the map
            this.map = L.map('map', {
                zoomControl: false
            }).setView([APP_CONFIG.centerLat, APP_CONFIG.centerLon], APP_CONFIG.defaultZoom);
            
            // Add OpenStreetMap tiles
            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: '© OpenStreetMap contributors',
                maxZoom: 20,
                minZoom: 14
            }).addTo(this.map);
            
            // Set map bounds to UG campus
            const bounds = L.latLngBounds(
                [APP_CONFIG.bounds.south, APP_CONFIG.bounds.west],
                [APP_CONFIG.bounds.north, APP_CONFIG.bounds.east]
            );
            this.map.setMaxBounds(bounds);
            this.map.fitBounds(bounds);
            
            // Map event listeners
            this.map.on('load', () => {
                document.querySelector('.map-container').classList.add('map-loaded');
                resolve();
            });
            
            this.map.on('click', (e) => {
                console.log('Clicked at:', e.latlng);
            });
            
            // Trigger load event if map is already loaded
            setTimeout(() => {
                document.querySelector('.map-container').classList.add('map-loaded');
                resolve();
            }, 1000);
        });
    }
    
    async loadLocations() {
        try {
            const response = await fetch('/api/locations');
            if (!response.ok) {
                throw new Error('Failed to load locations');
            }
            
            this.locations = await response.json();
            this.displayLocations(this.locations);
            this.populateLocationSelects();
            this.renderLocationsList();
        } catch (error) {
            console.error('Error loading locations:', error);
            // Load sample data if API fails
            this.loadSampleData();
        }
    }
    
    loadSampleData() {
        this.locations = [
            {
                id: 1,
                name: "Main Library",
                description: "Balme Library - The main university library",
                latitude: 5.6484,
                longitude: -0.1864,
                type: "LIBRARY"
            },
            {
                id: 2,
                name: "Great Hall",
                description: "University of Ghana Great Hall for ceremonies",
                latitude: 5.6490,
                longitude: -0.1870,
                type: "AUDITORIUM"
            },
            {
                id: 3,
                name: "Commonwealth Hall",
                description: "Commonwealth Hall - Student residential facility",
                latitude: 5.6475,
                longitude: -0.1850,
                type: "RESIDENTIAL_HALL"
            },
            {
                id: 4,
                name: "School of Medicine",
                description: "University of Ghana Medical School",
                latitude: 5.6500,
                longitude: -0.1880,
                type: "ACADEMIC_BUILDING"
            },
            {
                id: 5,
                name: "Sports Complex",
                description: "University sports and recreation complex",
                latitude: 5.6465,
                longitude: -0.1845,
                type: "SPORTS_FACILITY"
            },
            {
                id: 6,
                name: "Main Gate",
                description: "Main entrance to University of Ghana",
                latitude: 5.6520,
                longitude: -0.1890,
                type: "ENTRANCE_GATE"
            },
            {
                id: 7,
                name: "Student Centre",
                description: "Central hub for student activities",
                latitude: 5.6480,
                longitude: -0.1860,
                type: "ADMINISTRATIVE_BUILDING"
            },
            {
                id: 8,
                name: "Volta Hall",
                description: "Volta Hall - Student residential facility",
                latitude: 5.6470,
                longitude: -0.1875,
                type: "RESIDENTIAL_HALL"
            }
        ];
        
        this.displayLocations(this.locations);
        this.populateLocationSelects();
        this.renderLocationsList();
    }
    
    displayLocations(locations) {
        // Clear existing markers
        this.markers.forEach(marker => this.map.removeLayer(marker));
        this.markers = [];
        
        locations.forEach(location => {
            const marker = this.createLocationMarker(location);
            this.markers.push(marker);
        });
    }
    
    createLocationMarker(location) {
        const icon = this.getLocationIcon(location.type);
        
        const marker = L.marker([location.latitude, location.longitude], {
            icon: icon
        }).addTo(this.map);
        
        const popupContent = `
            <div class="location-popup">
                <h4>${location.name}</h4>
                <p class="location-type">${this.formatLocationType(location.type)}</p>
                ${location.description ? `<p>${location.description}</p>` : ''}
                <div class="popup-actions">
                    <button class="btn btn-primary btn-sm" onclick="navigator.showLocationDetails(${location.id})">
                        <i class="fas fa-info-circle"></i> Details
                    </button>
                    <button class="btn btn-success btn-sm" onclick="navigator.getDirectionsTo(${location.id})">
                        <i class="fas fa-directions"></i> Directions
                    </button>
                </div>
            </div>
        `;
        
        marker.bindPopup(popupContent, {
            className: 'custom-popup',
            maxWidth: 300
        });
        
        return marker;
    }
    
    getLocationIcon(type) {
        const iconColors = {
            'LIBRARY': '#e74c3c',
            'ACADEMIC_BUILDING': '#3498db',
            'ADMINISTRATIVE_BUILDING': '#9b59b6',
            'RESIDENTIAL_HALL': '#f39c12',
            'DINING_HALL': '#27ae60',
            'SPORTS_FACILITY': '#e67e22',
            'MEDICAL_CENTER': '#e74c3c',
            'PARKING': '#95a5a6',
            'ENTRANCE_GATE': '#34495e',
            'AUDITORIUM': '#8e44ad',
            'LANDMARK': '#16a085'
        };
        
        const color = iconColors[type] || '#667eea';
        
        return L.divIcon({
            className: 'custom-marker',
            html: `<div style="background-color: ${color}; width: 20px; height: 20px; border-radius: 50%; border: 2px solid white; box-shadow: 0 2px 4px rgba(0,0,0,0.3);"></div>`,
            iconSize: [24, 24],
            iconAnchor: [12, 12]
        });
    }
    
    formatLocationType(type) {
        return type.replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase());
    }
    
    populateLocationSelects() {
        const fromSelect = document.getElementById('fromLocation');
        const toSelect = document.getElementById('toLocation');
        
        // Clear existing options
        fromSelect.innerHTML = '<option value="">Select starting point...</option>';
        toSelect.innerHTML = '<option value="">Select destination...</option>';
        
        // Add current location option
        fromSelect.innerHTML += '<option value="current">📍 My Current Location</option>';
        
        this.locations.forEach(location => {
            const option = `<option value="${location.id}">${location.name}</option>`;
            fromSelect.innerHTML += option;
            toSelect.innerHTML += option;
        });
    }
    
    renderLocationsList() {
        const listContainer = document.getElementById('locationsList');
        
        if (this.locations.length === 0) {
            listContainer.innerHTML = '<p class="no-results">No locations found</p>';
            return;
        }
        
        listContainer.innerHTML = this.locations.map(location => `
            <div class="location-item" onclick="navigator.focusLocation(${location.id})">
                <div class="location-name">${location.name}</div>
                <div class="location-type">${this.formatLocationType(location.type)}</div>
                ${location.description ? `<div class="location-description">${location.description}</div>` : ''}
            </div>
        `).join('');
    }
    
    setupEventListeners() {
        // Search functionality
        const searchInput = document.getElementById('searchInput');
        const searchBtn = document.getElementById('searchBtn');
        
        const performSearch = () => {
            const query = searchInput.value.trim();
            this.searchLocations(query);
        };
        
        searchBtn.addEventListener('click', performSearch);
        searchInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                performSearch();
            }
        });
        
        // Type filter
        document.getElementById('typeFilter').addEventListener('change', (e) => {
            this.filterByType(e.target.value);
        });
        
        // Navigation controls
        document.getElementById('getRouteBtn').addEventListener('click', () => {
            this.calculateRoute();
        });
        
        document.getElementById('clearRouteBtn').addEventListener('click', () => {
            this.clearRoute();
        });
        
        // Location button
        document.getElementById('locationBtn').addEventListener('click', () => {
            this.getCurrentLocation();
        });
        
        // Sidebar toggle
        document.getElementById('toggleSidebar').addEventListener('click', () => {
            this.toggleSidebar();
        });
        
        // Map controls
        document.getElementById('zoomInBtn').addEventListener('click', () => {
            this.map.zoomIn();
        });
        
        document.getElementById('zoomOutBtn').addEventListener('click', () => {
            this.map.zoomOut();
        });
        
        document.getElementById('fullscreenBtn').addEventListener('click', () => {
            this.toggleFullscreen();
        });
        
        // Modal controls
        document.querySelector('.modal .close').addEventListener('click', () => {
            this.closeModal();
        });
        
        document.querySelector('.modal-close').addEventListener('click', () => {
            this.closeModal();
        });
        
        window.addEventListener('click', (e) => {
            const modal = document.getElementById('locationModal');
            if (e.target === modal) {
                this.closeModal();
            }
        });
    }
    
    async searchLocations(query) {
        try {
            if (!query) {
                this.displayLocations(this.locations);
                this.renderLocationsList();
                return;
            }
            
            // Filter locations locally (in production, this would be an API call)
            const filtered = this.locations.filter(location => 
                location.name.toLowerCase().includes(query.toLowerCase()) ||
                (location.description && location.description.toLowerCase().includes(query.toLowerCase()))
            );
            
            this.displayLocations(filtered);
            this.renderLocationsList();
            
        } catch (error) {
            console.error('Error searching locations:', error);
            this.showError('Failed to search locations');
        }
    }
    
    filterByType(type) {
        if (!type) {
            this.displayLocations(this.locations);
            this.renderLocationsList();
            return;
        }
        
        const filtered = this.locations.filter(location => location.type === type);
        this.displayLocations(filtered);
        this.renderLocationsList();
    }
    
    focusLocation(locationId) {
        const location = this.locations.find(l => l.id === locationId);
        if (location) {
            this.map.setView([location.latitude, location.longitude], 18);
            
            // Find and open the marker popup
            const marker = this.markers.find(m => 
                m.getLatLng().lat === location.latitude && 
                m.getLatLng().lng === location.longitude
            );
            if (marker) {
                marker.openPopup();
            }
        }
    }
    
    showLocationDetails(locationId) {
        const location = this.locations.find(l => l.id === locationId);
        if (!location) return;
        
        const modal = document.getElementById('locationModal');
        const modalTitle = document.getElementById('modalTitle');
        const modalContent = document.getElementById('modalContent');
        
        modalTitle.textContent = location.name;
        modalContent.innerHTML = `
            <div class="location-details">
                <p><strong>Type:</strong> ${this.formatLocationType(location.type)}</p>
                <p><strong>Coordinates:</strong> ${location.latitude.toFixed(6)}, ${location.longitude.toFixed(6)}</p>
                ${location.description ? `<p><strong>Description:</strong> ${location.description}</p>` : ''}
            </div>
        `;
        
        document.getElementById('getDirectionsBtn').onclick = () => {
            this.getDirectionsTo(locationId);
            this.closeModal();
        };
        
        modal.style.display = 'block';
    }
    
    closeModal() {
        document.getElementById('locationModal').style.display = 'none';
    }
    
    async getCurrentLocation() {
        if (!navigator.geolocation) {
            this.showError('Geolocation is not supported by this browser');
            return;
        }
        
        try {
            const position = await new Promise((resolve, reject) => {
                navigator.geolocation.getCurrentPosition(resolve, reject, {
                    enableHighAccuracy: true,
                    timeout: 10000,
                    maximumAge: 60000
                });
            });
            
            const { latitude, longitude } = position.coords;
            this.userLocation = { latitude, longitude };
            
            // Remove existing user location marker
            if (this.currentLocationMarker) {
                this.map.removeLayer(this.currentLocationMarker);
            }
            
            // Add user location marker
            this.currentLocationMarker = L.marker([latitude, longitude], {
                icon: L.divIcon({
                    className: 'current-location-marker',
                    html: '<div style="background-color: #007bff; width: 16px; height: 16px; border-radius: 50%; border: 3px solid white; box-shadow: 0 2px 4px rgba(0,0,0,0.3);"></div>',
                    iconSize: [22, 22],
                    iconAnchor: [11, 11]
                })
            }).addTo(this.map);
            
            this.currentLocationMarker.bindPopup('📍 Your Current Location');
            this.map.setView([latitude, longitude], 17);
            
        } catch (error) {
            console.error('Error getting location:', error);
            this.showError('Failed to get your current location');
        }
    }
    
    calculateRoute() {
        const fromSelect = document.getElementById('fromLocation');
        const toSelect = document.getElementById('toLocation');
        
        const fromValue = fromSelect.value;
        const toValue = toSelect.value;
        
        if (!fromValue || !toValue) {
            this.showError('Please select both starting point and destination');
            return;
        }
        
        let fromLocation, toLocation;
        
        // Get from location
        if (fromValue === 'current') {
            if (!this.userLocation) {
                this.showError('Current location not available. Please enable location services.');
                return;
            }
            fromLocation = this.userLocation;
        } else {
            const loc = this.locations.find(l => l.id == fromValue);
            if (!loc) return;
            fromLocation = { latitude: loc.latitude, longitude: loc.longitude };
        }
        
        // Get to location
        const loc = this.locations.find(l => l.id == toValue);
        if (!loc) return;
        toLocation = { latitude: loc.latitude, longitude: loc.longitude };
        
        this.displayRoute(fromLocation, toLocation);
    }
    
    displayRoute(from, to) {
        // Clear existing route
        this.clearRoute();
        
        // Create routing control
        this.currentRoute = L.Routing.control({
            waypoints: [
                L.latLng(from.latitude, from.longitude),
                L.latLng(to.latitude, to.longitude)
            ],
            routeWhileDragging: false,
            addWaypoints: false,
            createMarker: function() { return null; }, // Don't create default markers
            lineOptions: {
                styles: [{ color: '#667eea', weight: 6, opacity: 0.8 }]
            }
        }).addTo(this.map);
        
        document.getElementById('clearRouteBtn').style.display = 'inline-flex';
    }
    
    clearRoute() {
        if (this.currentRoute) {
            this.map.removeControl(this.currentRoute);
            this.currentRoute = null;
        }
        document.getElementById('clearRouteBtn').style.display = 'none';
    }
    
    getDirectionsTo(locationId) {
        document.getElementById('toLocation').value = locationId;
        if (this.userLocation) {
            document.getElementById('fromLocation').value = 'current';
        }
        this.calculateRoute();
    }
    
    toggleSidebar() {
        const sidebar = document.getElementById('sidebar');
        sidebar.classList.toggle('open');
    }
    
    toggleFullscreen() {
        if (!document.fullscreenElement) {
            document.documentElement.requestFullscreen();
        } else {
            document.exitFullscreen();
        }
    }
    
    hideLoading() {
        const loadingOverlay = document.getElementById('loadingOverlay');
        if (loadingOverlay) {
            loadingOverlay.style.display = 'none';
        }
    }
    
    showError(message) {
        // Simple error display - in production, use a proper notification system
        alert(message);
    }
}

// Initialize the application when the DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    window.navigator = new UGCampusNavigator();
});
