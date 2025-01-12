<!-- components/NestedMenu.vue -->
<template>
  <div>
    <draggable
        class="space-y-1"
        tag="ul"
        :list="menus"
        handle=".handle"
        :group="{ name: 'menu' }"
        item-key="id"
        @change="$emit('update:menus', menus)"
    >
      <template #item="{ element }">
        <li class="list-none">
          <MenuItem
              :menu="element"
              @edit="editMenu"
              @remove="removeMenu"
          />
          <div class="pl-8 mt-2">
            <nested-menu
                :menus="element.menus"
                @update:menus="updateSubMenus(element, $event)"
            />
          </div>
        </li>
      </template>
    </draggable>

  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue'
import draggable from 'vuedraggable'
import MenuItem from "@/components/sitemap/MenuItem.vue";

const props = defineProps({
  menus: {
    type: Array,
    required: true
  }
})

const emit = defineEmits(['update:menus'])

</script>