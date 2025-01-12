<!-- components/MenuItem.vue -->
<template>
  <div
      class="flex items-center justify-between px-2 py-1 lg:p-2 bg-white border rounded-lg shadow-sm"
      :class="{ 'opacity-60': !menu.isVisible }"
  >
    <div class="flex items-center space-x-2 lg:space-x-4 flex-1">
      <svg
          xmlns="http://www.w3.org/2000/svg"
          class="handle h-5 w-5 text-gray-400 cursor-move"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
      >
        <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M4 6h16M4 12h16M4 18h16"
        />
      </svg>
      <div class="flex-1">
        <p class="font-medium text-gray-800 text-xs lg:text-md">{{ menu.name }}</p>
        <p class="text-gray-500 text-xs lg:text-md">{{ menu.url }}</p>
      </div>
    </div>
    <div class="flex items-center space-x-2">
      <button
          @click="$emit('edit', menu)"
          class="p-1 text-blue-500 hover:bg-blue-50 rounded-md transition-colors"
      >
        <svg
            xmlns="http://www.w3.org/2000/svg"
            class="h-5 w-5"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
        >
          <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"
          />
        </svg>
      </button>
      <button
          @click="$emit('remove', menu)"
          class="p-1 text-red-500 hover:bg-red-50 rounded-md transition-colors"
      >
        <svg
            xmlns="http://www.w3.org/2000/svg"
            class="h-5 w-5"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
        >
          <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"
          />
        </svg>
      </button>
    </div>
  </div>
</template>

<script setup>
defineProps({
  menu: {
    type: Object,
    required: true
  }
})

defineEmits(['edit', 'remove'])
</script>